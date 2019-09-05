import py.optimize.rmse_function as rf
import py.optimize.optimizer as opt
import numpy as np
import sys


class GeneticAlgorithmOptimizer(opt.Optimizer):

    def __init__(self, boundaries, epoch_num=1000, seed_num=100,
                 crossover_probability=0.88, mutation_probability=0.10):
        self.boundaries = boundaries
        self.epoch_num = epoch_num
        self.seed_num = seed_num
        self.crossover_probability = crossover_probability
        self.mutation_probability = mutation_probability

    def denormalize_params(self, params):
        params_num = len(params)
        denormalized_params = np.array(params)
        for i in range(params_num):
            min_boundary = self.boundaries[i, 0]
            max_boundary = self.boundaries[i, 1]
            new_value = np.multiply(params[i], max_boundary - min_boundary) + min_boundary
            denormalized_params[i] = new_value
        return denormalized_params

    def selection_operator(self, loss_function, params_population, args):
        params_num = params_population.shape[1]
        ratios = np.random.rand(self.seed_num)
        fitness_values = np.random.rand(self.seed_num)
        sum = 0.0
        for i in range(self.seed_num):
            denormalized_params = self.denormalize_params(params_population[i, :])
            fitness_values[i] = 1.0 / loss_function.fun(denormalized_params, args)
            sum = sum + fitness_values[i]
        for i in range(self.seed_num):
            ratios[i] = fitness_values[i] / sum

        # calculate the scopes
        last_threshold = 0.0
        for i in range(self.seed_num):
            ratios[i] = ratios[i] + last_threshold
            last_threshold = ratios[i]

        # do selection
        selected_indices = []
        for i in range(self.seed_num):
            random_value = np.random.rand()
            for j in range(self.seed_num):
                if random_value < ratios[j]:
                    selected_indices.append(j)
                    break
        selected_indices = np.array(selected_indices)

        for i in range(self.seed_num):
            params = np.random.rand(1, params_num)
            for j in range(params_num):
                params[0, j] = params_population[selected_indices[i], j]
            params_population[i, :] = params

        return params_population

    def crossover_operator(self, params_population):
        params_num = params_population.shape[1]
        new_population = np.array(params_population)

        # select the items that can do crossover
        selected_indices = []
        for i in range(self.seed_num):
            if np.random.rand() < self.crossover_probability:
                selected_indices.append(i)
        selected_indices = np.array(selected_indices)

        # drop the single one to guarantee that the number of selected indices is even
        if len(selected_indices) % 2 != 0:
            selected_indices = selected_indices[:len(selected_indices)-1];

        # do crossover
        for k in range(0, len(selected_indices), 2):
            i = selected_indices[k]
            j = selected_indices[k + 1]
            new_items = self.cross(params_population[i, :], params_population[j, :], params_num)
            new_population[i, :] = new_items[0, :]
            new_population[j, :] = new_items[1, :]

        return new_population

    def cross(self, item1, item2, bit_num):
        cross_bits = np.random.randint(bit_num)
        new_items = np.random.rand(2, bit_num)

        # generate the first one
        for i in range(cross_bits):
            new_items[0, i] = item1[i]
        for i in range(cross_bits + 1, bit_num):
            new_items[0, i] = item2[i]

        # generate the second one
        for i in range(cross_bits):
            new_items[1, i] = item2[i]
        for i in range(cross_bits + 1, bit_num):
            new_items[1, i] = item1[i]

        return new_items

    def mutation_operator(self, params_population):
        params_num = params_population.shape[1]
        for i in range(self.seed_num):
            for j in range(params_num):
                if np.random.rand() < self.mutation_probability:
                    params_population[i, j] = np.random.rand()
        return params_population

    def optimize(self, target_function, params, train_input, truth_output, to_wrap_rmse_function=True):
        if to_wrap_rmse_function == True:
            target_function = rf.RmseFunction(target_function)
        args = np.concatenate((train_input, truth_output), 1)
        params_num = params.shape[1]
        # Step 1. Initialize population
        params_population = np.random.rand(self.seed_num, params_num)
        # start optimizing
        best_params = np.random.rand(1, params_num)
        min_rmse = sys.maxsize
        for e in range(self.epoch_num):
            print("Epoch %d/%d" %(e+1, self.epoch_num), self.denormalize_params(best_params))
            # Step 2. Record the best parameters
            for j in range(self.seed_num):
                denormalized_params = self.denormalize_params(params_population[j, :])
                rmse = target_function.fun(denormalized_params, args)
                if rmse < min_rmse:
                    min_rmse = rmse
                    best_params = np.array(params_population[j, :])

            # Step 3. Execute the selection operator
            params_population = self.selection_operator(target_function, params_population, args)

            # Step 4. Execute the crossover operator
            params_population = self.crossover_operator(params_population)

            # Step 5. Execute the mutation operator
            params_population = self.mutation_operator(params_population)

        denormalized_best_params = self.denormalize_params(best_params)
        return denormalized_best_params
