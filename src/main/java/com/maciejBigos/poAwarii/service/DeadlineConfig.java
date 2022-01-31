package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.Deadline;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.repository.SpecialistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableScheduling
public class DeadlineConfig {

    @Autowired
    private final SpecialistRepository specialistRepository;

    public DeadlineConfig(SpecialistRepository specialistRepository) {
        this.specialistRepository = specialistRepository;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void processDeadlines() {
        List<SpecialistProfile> specialistProfileList = specialistRepository.findAll();
        for (SpecialistProfile specialistProfile : specialistProfileList) {
            boolean usage = specialistProfile.getDeadlineConfig().get(LocalDateTime.now().getDayOfWeek().getValue());
            List<Deadline> tmp = specialistProfile.getDeadlineList();
            tmp.remove(0);
            tmp.add(new Deadline(LocalDateTime.now().plusDays(13),true,usage));
            specialistProfile.setDeadlineList(tmp);
            specialistRepository.save(specialistProfile);
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void doSth() {
        System.out.println("JEBAC CIE KURWO");
    }
}
