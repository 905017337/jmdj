package com.jm.service;


import com.jm.controller.form.DeleteCustomerCarByIdForm;
import com.jm.controller.form.InsertCustomerCarForm;
import com.jm.controller.form.SearchCustomerCarListForm;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerCarService {

    void insertCustomerCar(InsertCustomerCarForm form);

    ArrayList<HashMap> searchCustomerCarList(SearchCustomerCarListForm form);

    int deleteCustomerCarById(DeleteCustomerCarByIdForm form);
}
