package org.step.configuration.web;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import org.step.configuration.DatabaseConfiguration;
import org.step.filter.CharsetFilter;
import org.step.filter.ValidateUserFilter;
import org.step.listener.ProjectRequestListener;
import org.step.listener.SessionCount;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.io.File;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{DatabaseConfiguration.class, WebMvcConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{new CharsetFilter(), new ValidateUserFilter()};
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        servletContext.addListener(ProjectRequestListener.class);
        servletContext.addListener(SessionCount.class);
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        final int maxSize = 5 * 1024 * 1024;

        // Директория для временного хранения файлов
        File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // Регистрация нашей конфигурации загрузчика файлов
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(
                uploadDirectory.getAbsolutePath(),
                maxSize,
                maxSize * 2,
                maxSize / 2
        );

        registration.setMultipartConfig(multipartConfigElement);
    }
}
