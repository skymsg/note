package org.example;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Hello world!
 *
 */
public class ParallelComputation
{
    public static void main( String[] args )
    {
        long start = System.currentTimeMillis();
        Flowable.range(1,10000000)
                .parallel()
                .runOn(Schedulers.computation())
                .map(v -> v*v)
                .sequential()
                .blockingSubscribe(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }
}
