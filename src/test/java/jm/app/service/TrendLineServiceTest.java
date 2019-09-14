package jm.app.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TrendLineServiceTest {

    @Test
    public void testTrendLineService() {
        Double[] y = new Double[]{400.0, 401.68, 399.395, 401.22, 407.21, 410.25, 414.31, 414.63, 414.84, 414.56,
                415.62, 409.79, 407.86, 407.53, 409.67, 405.5};
        List<Double> yAsList = Arrays.asList(y);
        TrendLineService trendLineService = new TrendLineService();
        List<Double> yHat = trendLineService.estimateValue(yAsList, TrendLineEnum.AUTO);
        System.out.println(yHat);
    }

}
