package com.maciejBigos.poAwarii.service;

import com.maciejBigos.poAwarii.model.Deadline;
import com.maciejBigos.poAwarii.model.SpecialistProfile;
import com.maciejBigos.poAwarii.repository.DeadlineRepository;
import com.maciejBigos.poAwarii.repository.SpecialistRepository;
import com.maciejBigos.poAwarii.security.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableScheduling
@Service
public class DeadlineConfig {


    private static final Logger logger = LoggerFactory.getLogger(DeadlineConfig.class);

    @Autowired
    private final SpecialistRepository specialistRepository;

    @Autowired
    private final DeadlineRepository deadlineRepository;

    public DeadlineConfig(SpecialistRepository specialistRepository, DeadlineRepository deadlineRepository) {
        this.specialistRepository = specialistRepository;
        this.deadlineRepository = deadlineRepository;
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void processDeadlines() {
        logger.info(String.format("cron started %s" ,LocalDateTime.now()));
        List<SpecialistProfile> specialistProfileList = specialistRepository.findAll();
        for (SpecialistProfile specialistProfile : specialistProfileList) {
            logger.info(String.format("Upgrading deadlines for %s specialist", specialistProfile.getId()));
            if (Objects.isNull(specialistProfile.getDeadlineConfig())) {
                continue;
            }
            boolean usage = specialistProfile.getDeadlineConfig().get(LocalDateTime.now().getDayOfWeek().getValue());
            List<Deadline> tmp = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
            tmp.sort(Comparator.comparing(Deadline::getDate));
            deadlineRepository.delete(tmp.get(0));
            logger.info("old deadline deleted");
            Long newId = deadlineRepository.save(new Deadline(LocalDateTime.now().plusDays(13),true,usage)).getId();
            logger.info(String.format("deadline saved with id %s" ,newId));
            tmp.add(new Deadline(LocalDateTime.now().plusDays(13),true,usage));
            specialistProfile.getDeadlineIds().remove(0);
            specialistProfile.getDeadlineIds().add(newId);
            specialistRepository.save(specialistProfile);
            logger.info(String.format("specialist with id %s saved", specialistProfile.getId()));
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void doSth() {
//        logger.info(String.format("cron started %s" ,LocalDateTime.now()));
//        SpecialistProfile specialistProfile = specialistRepository.findById(14L).get();
//        logger.info(String.format("Upgrading deadlines for %s specialist", specialistProfile.getId()));
//        boolean usage = specialistProfile.getDeadlineConfig().get(LocalDateTime.now().getDayOfWeek().getValue());
//        List<Deadline> tmp = deadlineRepository.findAllById(specialistProfile.getDeadlineIds());
//        deadlineRepository.delete(tmp.get(0));
//        logger.info("deadline deleted");
//        Long newId = deadlineRepository.save(new Deadline(LocalDateTime.now().plusDays(13),true,usage)).getId();
//        logger.info(String.format("deadline saved with id %s" ,newId));
//        tmp.add(new Deadline(LocalDateTime.now().plusDays(13),true,usage));
//        specialistProfile.getDeadlineIds().remove(0);
//        specialistProfile.getDeadlineIds().add(newId);
//        specialistRepository.save(specialistProfile);
//        logger.info(String.format("specialist with id %s saved", specialistProfile.getId()));

    }
}
