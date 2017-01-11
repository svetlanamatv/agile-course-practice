package ru.unn.agile.BinaryTree.infrastructure;

import ru.unn.agile.BinaryTree.viewmodel.BinaryTreeViewModel;
import ru.unn.agile.BinaryTree.viewmodel.BinaryTreeViewModelTest;

public class ViewModelWithTxtLoggerTests extends BinaryTreeViewModelTest {
    @Override
    public void setUp() {
        FileTxtLogger realLogger =
                new FileTxtLogger("./treeViewModel.log");
        super.setViewModel(new BinaryTreeViewModel(realLogger));
    }


}
