package com.mypersonalportifolio.food_delivery_api.infrastructure.reports;

import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.DailySalesProjectionDTO;
import com.mypersonalportifolio.food_delivery_api.application.use_case.statistics.ReportStatsService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Component
public class JasperReportsStatsService implements ReportStatsService {

    private final String DAILY_SALES_TEMPLATE_PATH = "classpath:report-templates/daily-sales.jasper";

    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public byte[] generateDailySalesReportPdf(List<DailySalesProjectionDTO> dailySalesProjectionSource) {
        try {
            var parameters = new HashMap<String,Object>();
            parameters.put("REPORT_LOCALE", Locale.of("pt", "BR"));

            System.out.println("---------------------------------");
            System.out.println(dailySalesProjectionSource.size());
            System.out.println("---------------------------------");
            var datasource = new JRBeanCollectionDataSource(dailySalesProjectionSource);

            var jasperPrint = JasperFillManager.fillReport(loadDailySalesReportTemplate().getInputStream(), parameters, datasource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (IOException | JRException e) {

            throw new FailOnBuildReportException(e.getCause());
        }
    }

    private Resource loadDailySalesReportTemplate() {
        return resourceLoader.getResource(DAILY_SALES_TEMPLATE_PATH);
    }
}
