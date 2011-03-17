package com.thoughtworks.com;

import com.thoughtworks.web.RetrieveServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveServletTest {
    private RetrieveServlet retrieveServlet;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletOutputStream servletOutputStream;

    @Before
    public void setUp() throws Exception {
        retrieveServlet = new RetrieveServlet();
    }

    @Test
    public void shouldWriteTheGivenFileInResponse() throws IOException, ServletException {
        when(response.getOutputStream()).thenReturn(servletOutputStream);
        File file = File.createTempFile("tempZip",".zip",new File("/tmp"));
        file.deleteOnExit();
        retrieveServlet.sendFile(response, file);
        verify(servletOutputStream).write(Matchers.<byte[]>anyObject());
    }

    @Test
    public void shouldGetTomCatLogsZippedIfServletPathContainsAppLogs() throws IOException {
        when(request.getServletPath()).thenReturn("/appLogs");
        retrieveServlet.getFile(request);

    }

}
