import py.service.trend_line_service as tls
import py.service.trend_line_enum as tle

def test_trend_line_service():
    y = [400.0, 401.68, 399.395, 401.22, 407.21, 410.25, 414.31, 414.63, 414.84, 414.56,
         415.62, 409.79, 407.86, 407.53, 409.67, 405.5]
    trend_line_service = tls.TrendLineService()
    y_hat = trend_line_service.estimate_values(y, tle.TrendLineEnum.AUTO)
    print(y_hat)

if __name__ == "__main__":
    test_trend_line_service()