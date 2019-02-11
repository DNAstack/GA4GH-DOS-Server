package com.dnastack.dos.server.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Ga4ghDataObjectControllerTest {

    @Autowired
    private Ga4ghDataObjectController ga4ghDataObjectController;

    @Test
    public void DataObjectContexLoads() throws Exception {
        assertNotNull(ga4ghDataObjectController);
    }

}
