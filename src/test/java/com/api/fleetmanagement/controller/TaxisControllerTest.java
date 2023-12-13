package com.api.fleetmanagement.controller;

import com.api.fleetmanagement.FleetmanagementApplication;
import com.api.fleetmanagement.models.TaxisModel;
import com.api.fleetmanagement.repositories.TaxisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

public class TaxisControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TaxisRepository taxisRepository;
    @Test
    void getAllTaxisTest() throws Exception{
        TaxisModel taxis = new TaxisModel();
        taxis.setId(7249);
        taxis.setPlate("CNCJ-2997");

        Page<TaxisModel> page= new PageImpl<TaxisModel>(List.of(taxis));
        Mockito.when(taxisRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);
        this.mockMvc.perform(get("/taxis"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':7249,'plate':'CNCJ-2997'}]"
                ));
    }

}