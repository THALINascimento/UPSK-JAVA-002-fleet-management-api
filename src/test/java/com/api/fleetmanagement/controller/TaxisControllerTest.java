package com.api.fleetmanagement.controller;

import com.api.fleetmanagement.models.TaxisModel;
import com.api.fleetmanagement.repositories.TaxisRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaxisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaxisRepository taxisRepository;

    @Test
    void getAllTaxis() throws Exception {
        TaxisModel taxis = new TaxisModel();
        taxis.setPlate("CNCJ-2997");
        taxis.setId(7249);

        Page<TaxisModel> page = new PageImpl<>(List.of(taxis));

        Mockito.when(taxisRepository.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(page);

        this.mockMvc.perform(get("/taxis"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'content':[{'id':7249,'plate':'CNCJ-2997'}],'pageable':'INSTANCE','last':true,'totalPages':1,'totalElements':1,'first':true,'size':1,'number':0,'sort':{'sorted':false,'empty':true,'unsorted':true},'numberOfElements':1,'empty':false}"));

    }
}
