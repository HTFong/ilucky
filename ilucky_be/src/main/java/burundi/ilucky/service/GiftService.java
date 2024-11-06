package burundi.ilucky.service;

import burundi.ilucky.model.Gift;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftService {
    public static Map<String, Integer> giftPercents;

    @Value("#{${giftPercent.map}}")
    public void setGiftPercents(Map<String, Integer> giftPercentMap) {giftPercents = giftPercentMap;}

    public static Map<String, Gift> gifts;

    static {
        gifts = new HashMap<>();
        gifts.put("10000VND", new Gift("10000VND", "10.000 VND", 10000, "VND"));
        gifts.put("1000VND", new Gift("1000VND", "1.000 VND", 1000, "VND"));
        gifts.put("500VND", new Gift("500VND", "500 VND", 500, "VND"));
        gifts.put("200VND", new Gift("200VND", "200 VND", 200, "VND"));

        //Mete: Mảnh ghép
        gifts.put("SAMSUNG1", new Gift("SAMSUNG1", "Mảnh Samsung 1", 1, "SAMSUNG"));
        gifts.put("SAMSUNG2", new Gift("SAMSUNG2", "Mảnh Samsung 2", 1, "SAMSUNG"));
        gifts.put("SAMSUNG3", new Gift("SAMSUNG3", "Mảnh Samsung 3", 1, "SAMSUNG"));
        gifts.put("SAMSUNG4", new Gift("SAMSUNG4", "Mảnh Samsung 4", 1, "SAMSUNG"));

        //Let: Chữ cái
        gifts.put("L", new Gift("L", "1 Chữ cái \"L\"", 1, "PIECE"));
        gifts.put("I", new Gift("I", "1 Chữ cái \"I\"", 1, "PIECE"));
        gifts.put("T", new Gift("T", "1 Chữ cái \"T\"", 1, "PIECE"));
        gifts.put("E", new Gift("E", "1 Chữ cái \"E\"", 1, "PIECE"));

        gifts.put("SHARE", new Gift("SHARE", "Chia sẻ cho bạn bè để nhận được 1 lượt chơi", 1, "SHARE"));

        gifts.put("5555STARS", new Gift("5555STARS", "5555 Sao", 4, "STARS"));
        gifts.put("555STARS", new Gift("555STARS", "555 Sao", 3, "STARS"));
        gifts.put("55STARS", new Gift("55STARS", "55 Sao", 2, "STARS"));
        gifts.put("5STARS", new Gift("5STARS", "5 Sao", 1, "STARS"));

        gifts.put("UNLUCKY", new Gift("UNLUCKY", "Chúc bạn may mắn lần sau", 1, "UNLUCKY"));
    }

    public static Gift getRandomGift() {
        int randomPoint = new Random().nextInt((100 + 1) - 1) + 1;
        Map<String, Integer> giftPercentsMap = giftPercents.entrySet().stream()
                .filter(e -> e.getValue() != 0)
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue())); // remove element with 0%

        int sumPercent = 0;
        for (Map.Entry<String, Integer> entry : giftPercentsMap.entrySet()) {
            sumPercent+= entry.getValue();
            entry.setValue(sumPercent);
        }
        Map<Integer, String> giftPercentsMapInversed =
                giftPercentsMap.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));


        List<Integer> percentPoints = new ArrayList<>(giftPercentsMapInversed.keySet());
        Collections.sort(percentPoints);
        if (randomPoint<= percentPoints.get(0)) {
            return gifts.get(giftPercentsMapInversed.get(percentPoints.get(0)));
        } else {
            for (int i = 1; i < percentPoints.size(); i++) {
                if (percentPoints.get(i-1)<randomPoint && randomPoint<= percentPoints.get(i)) {
                    String randomKey = giftPercentsMapInversed.get(percentPoints.get(i));
                    return gifts.get(randomKey);
                }
            }
        }

        return null;
    }
}
