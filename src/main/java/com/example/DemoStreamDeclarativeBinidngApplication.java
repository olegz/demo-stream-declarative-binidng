package com.example;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoStreamDeclarativeBinidngApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoStreamDeclarativeBinidngApplication.class, args);
	}
	
	@Bean
	public Function<Flux<String>, Flux<String>> consume() {
		System.out.println();
		return flux -> flux.map(v -> {
			System.out.println("FOO: " + v);
			return v.toUpperCase();
		});
	}
	
//	@Bean
//	public Function<Flux<String>, Flux<String>> bar() {
//		return flux -> flux.map(v -> {
//			System.out.println("BAR: " + v);
//			return v.toUpperCase();
//		});
//	}
	
	@Bean
	public Supplier<Flux<Long>> produce() {
		return () -> Flux.interval(Duration.ofSeconds(1))
				.doOnNext(l -> {
					System.out.println("Producing long " + l);
				})
				.onErrorContinue((x,y) -> {
					System.out.println("ERROR: " + y);
				});
	}

}
