package de.clearit.kindergarten.domain;

import java.util.List;

import javax.swing.ListModel;

import org.javalite.activejdbc.Base;

import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.ObservableList;

import de.clearit.kindergarten.domain.entity.Purchase;

public class PurchaseBroker {

	public static final PurchaseBroker INSTANCE = new PurchaseBroker();

	private final ObservableList<PurchaseBean> purchase;

	// Instance Creation ******************************************************

	private PurchaseBroker() {
	  Base.open("org.sqlite.JDBC", "jdbc:sqlite:./kindergarten.sqlite", "", "");
	  purchase = new ArrayListModel<PurchaseBean>();
	  Purchase.<Purchase>findAll().stream().forEach(entity -> purchase.add(PurchaseBean.fromEntity(entity)));
	}

	// Public API *************************************************************

	public List<PurchaseBean> getList() {
	  return purchase;
	}

	public ListModel<?> getListModel() {
	  return purchase;
	}

	public void add(PurchaseBean purchase) {
	  purchase.add(purchase);
	  PurchaseBean.toEntity(purchase).saveIt();
	}

	public void remove(PurchaseBean purchase) {
	  purchase.remove(purchase);
	  PurchaseBean.toEntity(purchase).delete();
	}

	public PurchaseBean findById(Integer id, Integer itemNumber) {
	  for (PurchaseBean purchase : purchase) {
	    if (purchase.getSellerId().equals(id) && purchase.getItemNumber().equals(itemNumber)) {
	      return purchase;
	    }
	  }
	  return null;
	}

}