package de.clearit.validation.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdesktop.swingworker.SwingWorker;

import com.jgoodies.binding.beans.DelayedPropertyChangeHandler;
import com.jgoodies.validation.Validatable;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * A utility class that can be used by presentation models validate delayed and
 * in the background. You can use an instance of this class as a member field of
 * your PresentationModel subclass and delegate the validation work to it.
 */
public final class ValidationSupport {

  private static final Logger LOGGER = Logger.getLogger(ValidationSupport.class.getName());

  private final DelayedPropertyChangeHandler delayedValidationHandler;

  /**
   * Holds the ValidationResult and reports changes in that result.
   */
  private final ValidationResultModel model;

  /**
   * Used to perform the validation.
   */
  private Validatable validatable;

  /**
   * Used for the delayed validation.
   */
  private SwingWorker<ValidationResult, Object> worker;

  // Instance Creation ******************************************************

  public ValidationSupport(Validatable validatable) {
    this(validatable, DelayedPropertyChangeHandler.DEFAULT_DELAY);
  }

  /**
   * Creates a ValidationSupport for the given Validatable.
   *
   * @param validatable
   *          the Validatable that computes and returns the validation results
   *
   * @throws NullPointerException
   *           if the Validatable is is {@code null}.
   */
  public ValidationSupport(Validatable validatable, int delay) {
    if (validatable == null)
      throw new NullPointerException("The validatable must not be null.");

    this.validatable = validatable;
    this.delayedValidationHandler = new DelayedValidationHandler(delay);
    this.model = new DefaultValidationResultModel();
  }

  // API ********************************************************************

  private Validatable getValidatable() {
    return validatable;
  }

  private void setValidatable(Validatable newValidator) {
    if (newValidator == null) {
      throw new NullPointerException("The Validatable must not be null.");
    }
    delayedValidationHandler.stop();
    validatable = newValidator;
  }

  public ValidationResultModel resultModel() {
    return model;
  }

  public PropertyChangeListener delayedValidationHandler() {
    return delayedValidationHandler;
  }

  public synchronized ValidationResult getResult() {
    updateImmediately();
    return model.getResult();
  }

  // Implementation *********************************************************

  /**
   * Stops the delay handler if running, cancels a running worker - if any -,
   * validates, and finally sets the validation result in the model.
   */
  private void updateImmediately() {
    delayedValidationHandler.stop();
    if (worker != null) {
      worker.cancel(true);
    }
    LOGGER.log(Level.FINER, "Validating in foreground");
    resultModel().setResult(getValidatable().validate());
  }

  /**
   * Cancels a running worker - if any - and starts a new worker.
   *
   * TODO: Check if we shall cancel a running worker, or block until this worker
   * has finished.
   */
  private void updateDelayed() {
    if (worker != null) {
      worker.cancel(true);
    }
    worker = new ValidationWorker();
    worker.execute();
  }

  private final class ValidationWorker extends SwingWorker<ValidationResult, Object> {

    @Override
    protected ValidationResult doInBackground() {
      LOGGER.log(Level.FINER, "Validating in background");
      return getValidatable().validate();
    }

    @Override
    protected void done() {
      if (isCancelled()) {
        worker = null;
        return;
      }
      try {
        resultModel().setResult(get());
      } catch (InterruptedException e) {
        LOGGER.log(Level.FINEST, "ValidationWorker interrupted", e);
      } catch (ExecutionException e) {
        LOGGER.log(Level.INFO, "ValidationWorker execution failed", e);
      }
      worker = null;
    }

  }

  private final class DelayedValidationHandler extends DelayedPropertyChangeHandler {

    private DelayedValidationHandler(int delay) {
      super(delay, true);
    }

    @Override
    public void delayedPropertyChange(PropertyChangeEvent evt) {
      updateDelayed();
    }

  }

}
