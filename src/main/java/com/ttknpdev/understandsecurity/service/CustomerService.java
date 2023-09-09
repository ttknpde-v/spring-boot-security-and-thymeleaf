package com.ttknpdev.understandsecurity.service;

import com.ttknpdev.understandsecurity.entity.Customer;
import com.ttknpdev.understandsecurity.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerService implements CustomerRepository<Customer> {

    private List<Customer> data = new ArrayList<>();

    public CustomerService() {
        data.add(new Customer("Peter",(short)25,true));
        data.add(new Customer("Mark",(short)22,true));
        data.add(new Customer("Json",(short)21,true));
    }

    @Override
    public Customer read(String fullname) {
        Customer found = new Customer();
        data.forEach(customer -> {
            if (customer.getFullname().equals(fullname)) {
                found.setAge(customer.getAge());
                found.setFullname(customer.getFullname());
                found.setStatus(customer.getStatus());
            }
        });
        return found;
    }

    @Override
    public List<Customer> reads() {
        return data;
    }
}
