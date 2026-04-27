package com.webforj.samples.views.table.renderers;

public class Invoice {
  private String invoiceNumber;
  private String customer;
  private String customerEmail;
  private String issueDate;
  private String dueDate;
  private double amount;
  private double tax;
  private String status;
  private String paymentMethod;

  /**
   * @return the invoiceNumber
   */
  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  /**
   * @param invoiceNumber the invoiceNumber to set
   */
  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  /**
   * @return the customer
   */
  public String getCustomer() {
    return customer;
  }

  /**
   * @param customer the customer to set
   */
  public void setCustomer(String customer) {
    this.customer = customer;
  }

  /**
   * @return the customerEmail
   */
  public String getCustomerEmail() {
    return customerEmail;
  }

  /**
   * @param customerEmail the customerEmail to set
   */
  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  /**
   * @return the issueDate
   */
  public String getIssueDate() {
    return issueDate;
  }

  /**
   * @param issueDate the issueDate to set
   */
  public void setIssueDate(String issueDate) {
    this.issueDate = issueDate;
  }

  /**
   * @return the dueDate
   */
  public String getDueDate() {
    return dueDate;
  }

  /**
   * @param dueDate the dueDate to set
   */
  public void setDueDate(String dueDate) {
    this.dueDate = dueDate;
  }

  /**
   * @return the amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * @param amount the amount to set
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * @return the tax
   */
  public double getTax() {
    return tax;
  }

  /**
   * @param tax the tax to set
   */
  public void setTax(double tax) {
    this.tax = tax;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the paymentMethod
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }

  /**
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
}
