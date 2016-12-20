package ru.unn.agile.TemperatureConverter.infrastructure;

import ru.unn.agile.TemperatureConverter.viewmodel.TemperatureConverterViewModelTest;
import ru.unn.agile.TemperatureConverter.viewmodel.TemperatureConverterViewModel;
/**
 * Created by Jane on 16.12.2016.
 */
public class VMWithTxtLoggerTests extends TemperatureConverterViewModelTest {
         @Override
        public void setUp() {
            TxtLogger realLogger = new TxtLogger("VMWithTxtLoggerTests.log");
             super.setNewViewModel(new TemperatureConverterViewModel(realLogger));
        }

}
