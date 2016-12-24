package ru.unn.agile.triangle.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import ru.unn.agile.triangle.logging.Logger;
import ru.unn.agile.triangle.model.Circle;
import ru.unn.agile.triangle.model.Point2D;
import ru.unn.agile.triangle.model.Triangle;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Objects;

public class ViewModel {
    private final StringProperty ax = new SimpleStringProperty();
    private final StringProperty ay = new SimpleStringProperty();
    private final StringProperty bx = new SimpleStringProperty();
    private final StringProperty by = new SimpleStringProperty();
    private final StringProperty cx = new SimpleStringProperty();
    private final StringProperty cy = new SimpleStringProperty();
    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringProperty area = new SimpleStringProperty();
    private final StringProperty perimeter = new SimpleStringProperty();
    private final StringProperty circumcircleRadius = new SimpleStringProperty();
    private final StringProperty circumcircleCenterX = new SimpleStringProperty();
    private final StringProperty circumcircleCenterY = new SimpleStringProperty();
    private final StringProperty incircleRadius = new SimpleStringProperty();
    private final StringProperty incircleCenterX = new SimpleStringProperty();
    private final StringProperty incircleCenterY = new SimpleStringProperty();
    private final ObjectProperty<LoggerViewModel> loggerViewModel;

    private final Logger logger;

    public ViewModel(final Logger logger) {
        Objects.requireNonNull(logger);
        this.logger = logger;
        this.loggerViewModel = new SimpleObjectProperty<>(
                new LoggerViewModel(logger));

        logger.print(LoggerMessages.VIEW_MODEL_OBJECT_CREATING_STARTED);

        ax.set("");
        ay.set("");
        bx.set("");
        by.set("");
        cx.set("");
        cy.set("");

        calculationDisabled.setValue(true);
        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(ax, ay, bx, by, cx, cy);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        area.set("");
        perimeter.set("");
        circumcircleRadius.set("");
        circumcircleCenterX.set("");
        circumcircleCenterY.set("");
        incircleRadius.set("");
        incircleCenterX.set("");
        incircleCenterY.set("");

        logger.print(LoggerMessages.VIEW_MODEL_OBJECT_CREATING_FINISHED);
    }

    public void calculate() {
        logger.print(LoggerMessages.CALCULATING_STARTED);

        Point2D a = new Point2D(Double.parseDouble(ax.get()), Double.parseDouble(ay.get()));
        Point2D b = new Point2D(Double.parseDouble(bx.get()), Double.parseDouble(by.get()));
        Point2D c = new Point2D(Double.parseDouble(cx.get()), Double.parseDouble(cy.get()));

        logger.print(LoggerMessages.POINTS_COORDINATES_RECEIVED
                + " (a = {0}, b = {1}, c = {2})", a, b, c);

        Triangle triangle = new Triangle(a, b, c);

        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###.###", dfs);

        double areaValue = triangle.area();
        area.set(df.format(areaValue));

        logger.print(LoggerMessages.AREA_CALCULATED
                + " (S = {0})", areaValue);

        double perimeterValue = triangle.perimeter();
        perimeter.set(df.format(perimeterValue));

        logger.print(LoggerMessages.PERIMETER_CALCULATED
                + " (P = {0})", perimeterValue);

        Circle incircle = triangle.getIncircle();

        double incircleRadiusValue = incircle.getRadius();
        incircleRadius.set(df.format(incircleRadiusValue));

        logger.print(LoggerMessages.INCIRCLE_RADIUS_CALCULATED +
                " (R = {0})", incircleRadiusValue);

        Point2D incircleCenter = incircle.getCenter();
        incircleCenterX.setValue(df.format(incircleCenter.getX()));
        incircleCenterY.setValue(df.format(incircleCenter.getY()));

        logger.print(LoggerMessages.INCIRCLE_CENTER_CALCULATED +
                " (C = {0})", incircleCenter);

        if (areaValue == 0) {
            circumcircleRadius.set("undefined");
            circumcircleCenterX.set("undefined");
            circumcircleCenterY.set("undefined");
            logger.print(LoggerMessages.UNABLE_CALCULATE_CIRCUMCIRCLE_VALUES);
        } else {
            Circle circumcircle = triangle.getCircumcircle();

            double circumcircleRadiusValue = circumcircle.getRadius();
            circumcircleRadius.set(df.format(circumcircleRadiusValue));

            logger.print(LoggerMessages.CIRCUMCIRCLE_RADIUS_CALCULATED +
                    " (R = {0})", circumcircleRadiusValue);

            Point2D circumcircleCenter = circumcircle.getCenter();
            circumcircleCenterX.setValue(df.format(circumcircleCenter.getX()));
            circumcircleCenterY.setValue(df.format(circumcircleCenter.getY()));

            logger.print(LoggerMessages.CIRCUMCIRCLE_CENTER_CALCULATED +
                    " (C = {0})", circumcircleCenter);
        }
        
        logger.print(LoggerMessages.CALCULATING_FINISHED);
    }

    public StringProperty axProperty() {
        return ax;
    }

    public StringProperty ayProperty() {
        return ay;
    }

    public StringProperty bxProperty() {
        return bx;
    }

    public StringProperty byProperty() {
        return by;
    }

    public StringProperty cxProperty() {
        return cx;
    }

    public StringProperty cyProperty() {
        return cy;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public Boolean getCalculationDisabled() {
        return calculationDisabled.get();
    }

    public StringProperty areaProperty() {
        return area;
    }

    public String getArea() {
        return area.get();
    }

    public StringProperty perimeterProperty() {
        return perimeter;
    }

    public String getPerimeter() {
        return perimeter.get();
    }

    public StringProperty circumcircleRadiusProperty() {
        return circumcircleRadius;
    }

    public String getCircumcircleRadius() {
        return circumcircleRadius.get();
    }

    public StringProperty circumcircleCenterXProperty() {
        return circumcircleCenterX;
    }

    public String getCircumcircleCenterX() {
        return circumcircleCenterX.get();
    }

    public StringProperty circumcircleCenterYProperty() {
        return circumcircleCenterY;
    }

    public String getCircumcircleCenterY() {
        return circumcircleCenterY.get();
    }

    public StringProperty incircleRadiusProperty() {
        return incircleRadius;
    }

    public String getIncircleRadius() {
        return incircleRadius.get();
    }

    public StringProperty incircleCenterXProperty() {
        return incircleCenterX;
    }

    public String getIncircleCenterX() {
        return incircleCenterX.get();
    }

    public StringProperty incircleCenterYProperty() {
        return incircleCenterY;
    }

    public String getIncircleCenterY() {
        return incircleCenterY.get();
    }

    public final ReadOnlyObjectProperty<LoggerViewModel> loggerViewModelProperty() {
        return loggerViewModel;
    }

    public final LoggerViewModel getLoggerViewModel() {
        return loggerViewModel.get();
    }

    private Status getInputStatus() {
        logger.print(LoggerMessages.CHECKING_INPUT_STATUS);
        
        Status inputStatus = Status.READY;
        if (ax.get().isEmpty() || ay.get().isEmpty()
                || bx.get().isEmpty() || by.get().isEmpty()
                || cx.get().isEmpty() || cy.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!ax.get().isEmpty()) {
                Double.parseDouble(ax.get());
            }
            if (!ay.get().isEmpty()) {
                Double.parseDouble(ay.get());
            }
            if (!bx.get().isEmpty()) {
                Double.parseDouble(bx.get());
            }
            if (!by.get().isEmpty()) {
                Double.parseDouble(by.get());
            }
            if (!bx.get().isEmpty()) {
                Double.parseDouble(cx.get());
            }
            if (!by.get().isEmpty()) {
                Double.parseDouble(cy.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        logger.print(LoggerMessages.INPUT_STATUS_SET +
                " (value = '{0}')", inputStatus);
        return inputStatus;
    }

    enum Status {
        WAITING("Waiting"), READY("Ready"), BAD_FORMAT("Bad Format");

        private final String statusMessage;

        Status(final String message) {
            this.statusMessage = message;
        }

        @Override
        public String toString() {
            return statusMessage;
        }
    }

    final class LoggerMessages {
        static final String VIEW_MODEL_OBJECT_CREATING_STARTED =
                "Constructing new ViewModel object";
        static final String VIEW_MODEL_OBJECT_CREATING_FINISHED =
                "ViewModel object created";
        static final String CALCULATING_STARTED =
                "Calculating started";
        static final String POINTS_COORDINATES_RECEIVED =
                "Points coordinates received";
        static final String AREA_CALCULATED =
                "Area calculated";
        static final String PERIMETER_CALCULATED =
                "Perimeter calculated";
        static final String INCIRCLE_RADIUS_CALCULATED =
                "Incircle radius calculated";
        static final String INCIRCLE_CENTER_CALCULATED =
                "Incircle center calculated";
        static final String CIRCUMCIRCLE_RADIUS_CALCULATED =
                "Circumcircle radius calculated";
        static final String CIRCUMCIRCLE_CENTER_CALCULATED =
                "Circumcircle center calculated";
        static final String UNABLE_CALCULATE_CIRCUMCIRCLE_VALUES =
                "Unable to calculate circumcircle values";
        static final String CALCULATING_FINISHED =
                "Calculating finished";
        static final String CHECKING_INPUT_STATUS =
                "Checking input status";
        static final String INPUT_STATUS_SET =
                "Input status set to";
    }
}
