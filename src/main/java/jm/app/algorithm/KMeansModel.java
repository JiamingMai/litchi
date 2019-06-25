package jm.app.algorithm;

import jm.app.algebra.AlgebraUtil;
import jm.app.algebra.Matrix;

import java.math.BigDecimal;
import java.util.*;

public class KMeansModel {

    public Map<Integer, List<Integer>> cluster(Matrix featureMat, int k, int epochNum) {
        // Step 1. Normalize the input
        Matrix dataPoints = AlgebraUtil.normalize(featureMat, 0);

        // Step 2. Initialize k central points
        Matrix centralPoints = new Matrix(k, dataPoints.getColNum());
        int[] indices = genDistinctRandomNum(dataPoints.getRowNum(), k);
        for (int i = 0; i < indices.length; i++) {
            for (int c = 0; c < dataPoints.getColNum(); c++) {
                BigDecimal value = new BigDecimal(dataPoints.getValue(indices[i], c).doubleValue());
                centralPoints.setValue(i, c, value);
            }
        }

        // Step 3. Optimize the central points
        for (int i = 0; i < epochNum; i++) {
            Matrix[] clusters = clusterWithCentralPoints(dataPoints, centralPoints);
            centralPoints = updateCentralPoints(clusters);
        }
        Map<Integer, List<Integer>> clusterToDataPointIndices = getClusterToDataPointIndices(dataPoints, centralPoints);
        return clusterToDataPointIndices;
    }

    public Map<Integer, List<Integer>> getClusterToDataPointIndices(Matrix dataPoints, Matrix centralPoints) {
        Map<Integer, List<Integer>> clusterToDataPointIndices = new HashMap<>();
        for (int r = 0; r < dataPoints.getRowNum(); r++) {
            // clustering for the ith data point
            Matrix dataPoint = AlgebraUtil.getRow(dataPoints, r);
            BigDecimal minDistance = new BigDecimal(Integer.MAX_VALUE);
            int bestCluster = 0;
            for (int i = 0; i < centralPoints.getRowNum(); i++) {
                Matrix ithClusterCentralPoint = AlgebraUtil.getRow(centralPoints, i);
                BigDecimal distance = AlgebraUtil.calcEuclideanDistance(dataPoint, ithClusterCentralPoint);
                if (distance.compareTo(minDistance) < 0) {
                    minDistance = distance;
                    bestCluster = i;
                }
            }

            // record the clustering result of the ith data point
            List<Integer> dataPointIndices = clusterToDataPointIndices.get(bestCluster);
            if (null == dataPointIndices) {
                dataPointIndices = new ArrayList<>();
            }
            dataPointIndices.add(r);
            clusterToDataPointIndices.put(bestCluster, dataPointIndices);
        }
        return clusterToDataPointIndices;
    }

    private Matrix[] clusterWithCentralPoints(Matrix dataPoints, Matrix centralPoints) {
        Map<Integer, List<Integer>> clusterToDataPointIndices = getClusterToDataPointIndices(dataPoints, centralPoints);

        // build the result with the structure Matrix[]
        Matrix[] clusters = new Matrix[centralPoints.getRowNum()];
        for (int i = 0; i < clusters.length; i++) {
            List<Integer> ithClusterIndices = clusterToDataPointIndices.get(i);
            Matrix ithCluster = new Matrix(ithClusterIndices.size(), dataPoints.getColNum());
            for (int j = 0; j < ithClusterIndices.size(); j++) {
                int index = ithClusterIndices.get(j);
                Matrix dataPoint = AlgebraUtil.getRow(dataPoints, index);
                ithCluster = AlgebraUtil.setRowVector(ithCluster, j, dataPoint);
            }
            clusters[i] = ithCluster;
        }
        return clusters;
    }

    private Matrix updateCentralPoints(Matrix[] clusters) {
        Matrix centralPoints = new Matrix(clusters.length, clusters[0].getColNum());
        for (int i = 0; i < clusters.length; i++) {
            Matrix ithCentralPoint = AlgebraUtil.mean(clusters[i], 0);
            centralPoints = AlgebraUtil.setRowVector(centralPoints, i, ithCentralPoint);
        }
        return centralPoints;
    }

    private int[] genDistinctRandomNum(int bound, int numCount) {
        Random random = new Random();
        Set<Integer> indices = new HashSet<>();
        for (int i = 0; i < numCount; i++) {
            int index = random.nextInt(bound);
            if (indices.contains(index)) {
                i--;
                continue;
            }
            indices.add(index);
        }
        int[] randomNum = new int[numCount];
        Iterator<Integer> it = indices.iterator();
        int i = 0;
        while (it.hasNext()) {
            randomNum[i] = it.next();
            i++;
        }
        return randomNum;
    }

}
