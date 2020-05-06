package main.dao;

import main.entity.Customer;

import java.util.List;

public interface CustomerDAO {

	List<Customer> getCustomers();

	void saveCustomer(Customer customer);

	Customer getCustomer(int id);

	void deleteCustomer(int id);

	List<Customer> searchCustomers(String name);
}
