package ch.stephan.chickenfarm.timer;

import ch.stephan.chickenfarm.dto.Box;
import ch.stephan.chickenfarm.messenger.MessengerService;
import ch.stephan.chickenfarm.registry.BoxService;
import ch.stephan.chickenfarm.scale.ScaleService;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MorningScaleTaring {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Autowired
    private final BoxService boxService;

    @Autowired
    private final ScaleService scaleService;

    @Autowired
    private final MessengerService messengerService;

    @Scheduled(cron = "${schedulerservice.tare.cron}")
    public void measureWeights() {
        boxService.getBoxes().stream()//
                .map(Box::getId) //
                .forEach(id -> scaleService.tare(id));

        String messageId = messengerService.sendNotification(String.format(":nest_with_eggs: %s: Ein neuer Tag beginnt, die Waagen wurden soeben tariert...", dateFormat.format(new Date())));
        log.info("Message sent: {}", messageId);
    }


}
