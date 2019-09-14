import py.service.anomaly_detection_service as detector

def test_detect_anomaly():
    y = [400.0, 401.68, 399.395, 401.22, 407.21, 410.25, 414.31, 414.63, 414.84, 414.56,
         415.62, 409.79, 407.86, 407.53, 409.67, 405.5]
    anomaly_detection_service = detector.AnomalyDetionService()
    anomaly = anomaly_detection_service.detect_anomaly(y, 275.05)
    print(anomaly)

if __name__ == "__main__":
    test_detect_anomaly()