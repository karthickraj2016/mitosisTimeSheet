package com.mitosis.timesheet.dao;

import com.mitosis.timesheet.model.CustomerPaymentModel;

public interface BankReconcileDao {

	public CustomerPaymentModel getReceiptDetails(String ino);

}
