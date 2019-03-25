package jm.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmOptimizer implements Optimizer {

    private final int SEED_NUM = 100;

    private final double CROSSOVER_PROBABILITY = 0.88;

    private final double MUTATION_PROBABILITY = 0.10;

    private int epochNum = 10000;

    private Matrix boundaries;

    public GeneticAlgorithmOptimizer(Matrix boundaries) {
        this.boundaries = boundaries;
    }

    public GeneticAlgorithmOptimizer(Matrix boundaries, int epochNum) {
        this.boundaries = boundaries;
        this.epochNum = epochNum;
    }

    @Override
    public Matrix optimize(TargetFunction fun, Matrix params, Matrix args) {
        return null;
    }

    @Override
    public Matrix optimize(TargetFunction fun, Matrix params, Matrix trainInput, Matrix truthOutput) {
        RmseFunction rmseFunction = new RmseFunction(fun);
        Matrix args = AlgebraUtil.mergeMatrix(trainInput, truthOutput, 1);
        Random random = new Random();
        int paramsNum = params.getColNum();

        // Step 1. Initialize population
        Matrix paramsPopulation = new Matrix(SEED_NUM, paramsNum);
        for (int i = 0; i < SEED_NUM; i++) {
            for (int j = 0; j < paramsNum; j++) {
                paramsPopulation.setValue(i, j, new BigDecimal(random.nextDouble()));
            }
        }

        // start optimizing
        Matrix bestParams = new Matrix(1, paramsNum);
        BigDecimal minRmse = new BigDecimal(Integer.MAX_VALUE);
        for (int i = 0; i < epochNum; i++) {
            System.out.println(String.format("Epoch %d/%d", i + 1, epochNum));

            // Step 2. Record the best parameters
            for (int j = 0; j < SEED_NUM; j++) {
                Matrix denormalizedParams = denormalizeParams(AlgebraUtil.getRowVector(paramsPopulation, j));
                BigDecimal rmse = rmseFunction.fun(denormalizedParams, args);
                if (rmse.compareTo(minRmse) < 0) {
                    minRmse = rmse;
                    for (int k = 0; k < paramsNum; k++) {
                        BigDecimal value = new BigDecimal(paramsPopulation.getValue(j, k).doubleValue());
                        bestParams.setValue(0, k, value);
                    }
                }
            }

            // Step 3. Execute the selection operator
            paramsPopulation = selectionOperator(rmseFunction, paramsPopulation, args);

            // Step 4. Execute the crossover operator
            paramsPopulation = crossoverOperator(paramsPopulation);

            // Step 5. Execute the mutation operator
            paramsPopulation = mutationOperator(paramsPopulation);
        }

        Matrix denormalizedBestParams = denormalizeParams(bestParams);
        return denormalizedBestParams;
    }

    private Matrix denormalizeParams(Matrix params) {
        int paramsNum = params.getColNum();
        Matrix denormalizedParams = new Matrix(params.getRowNum(), params.getColNum());
        for (int i = 0; i < paramsNum; i++) {
            BigDecimal min = boundaries.getValue(i, 0);
            BigDecimal max = boundaries.getValue(i, 1);
            BigDecimal newValue = params.getValue(0, i).multiply(max.subtract(min)).add(min);
            denormalizedParams.setValue(0, i, newValue);
        }
        return denormalizedParams;
    }

    private Matrix normalizeParams(Matrix params) {
        int paramsNum = params.getColNum();
        Matrix normalizedParams = new Matrix(params.getRowNum(), params.getColNum());
        for (int i = 0; i < paramsNum; i++) {
            BigDecimal min = boundaries.getValue(i, 0);
            BigDecimal max = boundaries.getValue(i, 1);
            BigDecimal newValue = params.getValue(0, i).subtract(min).multiply(new BigDecimal(1.0 / max.subtract(min).doubleValue()));
            normalizedParams.setValue(0, i, newValue);
        }
        return normalizedParams;
    }

    private Matrix selectionOperator(TargetFunction lossFunction, Matrix paramsPopulation, Matrix args) {
        Random random = new Random();
        int paramsNum = paramsPopulation.getColNum();
        BigDecimal[] ratios = new BigDecimal[SEED_NUM];
        BigDecimal[] fitnessValues = new BigDecimal[SEED_NUM];
        BigDecimal sum = new BigDecimal(0.0);
        for (int i = 0; i < SEED_NUM; i++) {
            Matrix denormalizedParams = denormalizeParams(AlgebraUtil.getRowVector(paramsPopulation, i));
            BigDecimal fitnessValue = new BigDecimal(1.0 / lossFunction.fun(denormalizedParams, args).doubleValue());
            fitnessValues[i] = fitnessValue;
            sum = sum.add(fitnessValue);
        }
        for (int i = 0; i < SEED_NUM; i++) {
            ratios[i] = fitnessValues[i].multiply(new BigDecimal(1.0 / sum.doubleValue()));
        }
        // calculate the scopes
        BigDecimal lastThreshold = new BigDecimal(0.0);
        for (int i = 0; i < SEED_NUM; i++) {
            ratios[i] = ratios[i].add(lastThreshold);
            lastThreshold = new BigDecimal(ratios[i].doubleValue());
        }
        // do selection
        int[] selectedIndices = new int[SEED_NUM];
        for (int i = 0; i < SEED_NUM; i++) {
            double randomValue = random.nextDouble();
            for (int j = 0; j < SEED_NUM; j++) {
                if (randomValue < ratios[j].doubleValue()) {
                    selectedIndices[i] = j;
                    break;
                }
            }
        }
        for (int i = 0; i < SEED_NUM; i++) {
            Matrix params = new Matrix(1, paramsNum);
            for (int j = 0; j < paramsNum; j++) {
                params.setValue(0, j, new BigDecimal(paramsPopulation.getValue(selectedIndices[i], j).doubleValue()));
            }
            AlgebraUtil.setRowVector(paramsPopulation, i, params);
        }
        return paramsPopulation;
    }

    private Matrix cross(Matrix item1, Matrix item2, int bitNum) {
        Random random = new Random();
        int crossBits = random.nextInt(bitNum);
        Matrix newItems = new Matrix(2, item1.getColNum());

        // generate the first one
        for (int i = 0; i <= crossBits; i++) {
            BigDecimal value = new BigDecimal(item1.getValue(0, i).doubleValue());
            newItems.setValue(0, i, value);
        }
        for (int i = crossBits + 1; i < item1.getColNum(); i++) {
            BigDecimal value = new BigDecimal(item2.getValue(0, i).doubleValue());
            newItems.setValue(0, i, value);
        }

        // generate the second one
        for (int i =0; i <= crossBits; i++) {
            BigDecimal value = new BigDecimal(item2.getValue(0, i).doubleValue());
            newItems.setValue(1, i, value);
        }
        for (int i = crossBits + 1; i < item1.getColNum(); i++) {
            BigDecimal value = new BigDecimal(item1.getValue(0, i).doubleValue());
            newItems.setValue(1, i, value);
        }
        return newItems;
    }

    private Matrix crossoverOperator(Matrix paramsPopulation) {
        Random random = new Random();
        int paramsNum = paramsPopulation.getColNum();
        Matrix newPopulation = new Matrix(SEED_NUM, paramsNum);

        // select the items that can do crossover
        List<Integer> selectedIndices = new ArrayList<>();
        for (int i = 0; i < SEED_NUM; i++) {
            if (random.nextDouble() < CROSSOVER_PROBABILITY) {
                selectedIndices.add(i);
            }
        }
        if (selectedIndices.size() % 2 != 0) {
            int reservedItemIndex = selectedIndices.remove(selectedIndices.size() - 1);
            newPopulation = AlgebraUtil.setRowVector(newPopulation, SEED_NUM - 1, AlgebraUtil.getRowVector(paramsPopulation, reservedItemIndex));
        }

        // do crossover
        for (int i = 0; i < selectedIndices.size(); i += 2) {
            Matrix newItems = cross(AlgebraUtil.getRowVector(paramsPopulation, i), AlgebraUtil.getRowVector(paramsPopulation, i + 1), paramsNum);
            newPopulation = AlgebraUtil.setRowVector(newPopulation, i, AlgebraUtil.getRowVector(newItems, 0));
            newPopulation = AlgebraUtil.setRowVector(newPopulation, i + 1, AlgebraUtil.getRowVector(newItems, 1));
        }

        // reserve the items that do not cross
        for (int i = selectedIndices.size(); i < SEED_NUM; i++) {
            newPopulation = AlgebraUtil.setRowVector(newPopulation, i, AlgebraUtil.getRowVector(paramsPopulation, i));
        }
        return newPopulation;
    }

    private Matrix mutationOperator(Matrix paramsPopulation) {
        Random random = new Random();
        int paramsNum = paramsPopulation.getColNum();
        for (int i = 0; i < SEED_NUM; i++) {
            for (int j = 0; j < paramsNum; j++) {
                if (random.nextDouble() < MUTATION_PROBABILITY) {
                    paramsPopulation.setValue(i, j, new BigDecimal(random.nextDouble()));
                }
            }
        }
        return paramsPopulation;
    }
}
