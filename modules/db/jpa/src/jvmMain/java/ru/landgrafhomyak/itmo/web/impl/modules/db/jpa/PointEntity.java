package ru.landgrafhomyak.itmo.web.impl.modules.db.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import ru.landgrafhomyak.itmo.web.impl.modules.db.PointData;
import ru.landgrafhomyak.itmo.web.impl.utility.TimeDelta;
import ru.landgrafhomyak.itmo.web.impl.utility.TimePoint;


@Entity(name = "history")
class PointEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "x_raw")
    @org.jetbrains.annotations.NotNull
    @jakarta.validation.constraints.NotNull
    String xRaw;

    @Column(name = "y_raw")
    @org.jetbrains.annotations.NotNull
    @jakarta.validation.constraints.NotNull
    String yRaw;

    @Column(name = "r_raw")
    @org.jetbrains.annotations.NotNull
    @jakarta.validation.constraints.NotNull
    String rRaw;

    @Column(name = "x")
    @org.jetbrains.annotations.Nullable
    Double x;

    @Column(name = "y")
    @org.jetbrains.annotations.Nullable
    Double y;

    @Column(name = "r")
    @org.jetbrains.annotations.Nullable
    Double r;

    @Column(name = "result")
    @jakarta.validation.constraints.NotNull
    boolean result;

    @Column(name = "time")
    @jakarta.validation.constraints.NotNull
    long time;

    @Column(name = "exec_time")
    @jakarta.validation.constraints.NotNull
    long exec_time;

    static PointEntity fromPointData(PointData p) {
        PointEntity e = new PointEntity();
        e.xRaw = p.xRaw();
        e.yRaw = p.yRaw();
        e.rRaw = p.rRaw();
        e.x = p.x();
        e.y = p.y();
        e.r = p.r();
        e.result = p.result();
        e.time = p.time()._asULong();
        e.exec_time = p.execTime()._asULong();
        return e;
    }

    PointData toPointData() {
        return new PointData(
                this.xRaw,
                this.yRaw,
                this.rRaw,
                this.x,
                this.y,
                this.r,
                this.result,
                TimePoint._fromULong(this.time),
                TimeDelta._fromULong(this.exec_time)
        );
    }
}
