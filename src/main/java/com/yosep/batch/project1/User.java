package com.yosep.batch.project1;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends AuditingEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    @Enumerated(EnumType.STRING)
    private Level level;

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
                return GOLD;
            }

            if(totalAmount >= Level.SILVER.nextAmount) {
                return SILVER;
            }

            return NORMAL;
        }
    }
}
