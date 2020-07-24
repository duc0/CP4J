package com.vb.algorithm;

public final class AlgorithmRunner {
    private AlgorithmRunner() { }
    public static <I, O> AlgorithmResult<O> runAlgorithm(Algorithm<I, O> algorithm, I input) {
        return runAlgorithm(algorithm, input, 1000000000);
    }

    public static <I, O> AlgorithmResult<O> runAlgorithm(Algorithm<I, O> algorithm, I input, long complexityLimit) {
        long complexity = algorithm.getComplexity(input);
        if (complexity > complexityLimit) {
            throw new RuntimeException("This algorithm might be too slow!");
        }
        long startTime = System.currentTimeMillis();
        O output = algorithm.run(input);
        long elapsed = System.currentTimeMillis() - startTime;
        return new AlgorithmResult<>(output, elapsed, complexity);
    }
    
    public static final class AlgorithmResult<O> {
        final O result;
        final long runTime;
        final long complexity;

        AlgorithmResult(O result, long runTime, long complexity) {
            this.result = result;
            this.runTime = runTime;
            this.complexity = complexity;
        }

        public long getRunTime() {
            return runTime;
        }

        public O getResult() {
            return result;
        }

        public long getComplexity() {
            return complexity;
        }
    }
}
