package main.controller;

import main.entity.Customer;
import main.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer service
	@Autowired
	private CustomerService customerService;

	@GetMapping("/list")                            // only get request methods can be applied: restricted
	public String listCustomers(Model model) {

		// get customers from the dao
		List<Customer> theCustomers = this.customerService.getCustomers();

		// add the customers to the model
		model.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormAddCustomer(Model model) {

		// Create model attribute to bind form data
		Customer aCustomer = new Customer();
		model.addAttribute("customer", aCustomer);
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveNewCustomer(@ModelAttribute("customer") Customer customer) {

		// save customer using our CustomerService
		this.customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormAddUpdate(@RequestParam("customerId") int id, Model model) {
		// get the customer from the CustomerService
		Customer aCustomer = this.customerService.getCustomer(id);

		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", aCustomer);

		// send over to our form
		return "customer-form";
	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {

		// delete customer
		this.customerService.deleteCustomer(id);
		return "redirect:/customer/list";
	}

	@GetMapping("/search")
	public String searchCustomer(@RequestParam("theSearchName") String name, Model model) {

		// search customers from the service
		List<Customer> foundCustomers = this.customerService.searchCustomers(name);

		// add found customers to the model
		model.addAttribute("customers", foundCustomers);

		return "list-customers";
	}

}
