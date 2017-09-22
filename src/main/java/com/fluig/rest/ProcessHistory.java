package com.fluig.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.dao.DatabaseManagement;

@Path("/process")
public class ProcessHistory{

        private Logger log = LoggerFactory.getLogger(ProcessHistory.class);

        @GET
        @Path("/{cod_empresa}")
        @Produces({"application/json"})
        public Response getProcess(@PathParam("cod_empresa") long cod_empresa)
        {
          try
          {
            return Response.ok(new DatabaseManagement().findDefProces(cod_empresa))
              .build();
          } catch (Exception e) {
            e.printStackTrace();
          }return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Nao foi possivel carregar os processos")
            .type("application/json")
            .build();
        }

}
