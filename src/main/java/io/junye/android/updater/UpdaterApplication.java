package io.junye.android.updater;

import com.sun.jna.Native;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpdaterApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(UpdaterApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
//		Native.extractFromResourcePath("lib/bsdiff");
	}
}
