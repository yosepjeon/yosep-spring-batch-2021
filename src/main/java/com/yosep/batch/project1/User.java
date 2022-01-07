package com.yosep.batch.project1;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class User extends AuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Level level = Level.NORMAL;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Orders> orders;

    @Builder
    private User(String userName, List<Orders> orders) {
        this.userName = userName;
        this.orders = orders;
    }

    public boolean availableLeveUp() {
        return Level.availableLevelUp(this.getLevel(), this.getTotalAmount());
    }

    private int getTotalAmount() {
        return this.orders.stream()
                .mapToInt(Orders::getAmount)
                .sum();
    }

    public Level levelUp() {
        Level nextLevel = Level.getNextLevel(this.getTotalAmount());

        this.level = nextLevel;

        return nextLevel;
    }


    public enum Level {
        VIP(500_000, null),
        GOLD(500_000, VIP),
        SILVER(300_000, GOLD),
        NORMAL(200_000, SILVER);

        private final int nextAmount;
        private final Level nextLevel;

        Level(int nextAmount, Level nextLevel) {
            this.nextAmount = nextAmount;
            this.nextLevel = nextLevel;
        }

        private static boolean availableLevelUp(Level level, int totalAmount) {
            if(ObjectUtils.isEmpty(level)) {
                return false;
            }

            if(ObjectUtils.isEmpty(level.nextLevel)) {
                return false;
            }

            return totalAmount >= level.nextAmount;
        }

        private static Level getNextLevel(int totalAmount) {
            if(totalAmount >= Level.VIP.nextAmount) {
                return VIP;
            }

            if(totalAmount >= Level.GOLD.nextAmount) {
                return GOLD.nextLevel;
            }

            if(totalAmount >= Level.SILVER.nextAmount) {
                return SILVER.nextLevel;
            }

            if (totalAmount >= Level.NORMAL.nextAmount) {
                return NORMAL.nextLevel;
            }

            return NORMAL;
        }
    }
}
