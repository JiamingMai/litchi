import py.optimizer as opt


class GeneticAlgorithmOptimizer(opt.Optimizer):
    pass


May = GeneticAlgorithmOptimizer("May", "female")
Peter = opt.Optimizer("Peter", "male")

print(May.name, May.sex, Peter.name, Peter.sex)
May.print_title()
Peter.print_title()
