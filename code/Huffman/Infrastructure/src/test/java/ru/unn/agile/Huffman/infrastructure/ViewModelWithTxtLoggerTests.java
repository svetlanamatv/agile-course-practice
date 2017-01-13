package ru.unn.agile.Huffman.infrastructure;

import ru.unn.agile.Huffman.viewmodel.HuffmanViewModel;
import ru.unn.agile.Huffman.viewmodel.HuffmanViewModelTest;

public class ViewModelWithTxtLoggerTests extends HuffmanViewModelTest {
    @Override
    public void setUp() {
        FileTextLogger realLogger =
                new FileTextLogger("./treeViewModel.log");
        super.setViewModel(new HuffmanViewModel(realLogger));
    }


}
