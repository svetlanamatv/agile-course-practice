package ru.unn.agile.BinaryTree.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryTreeViewModelTest {
    private BinaryTreeViewModel viewModel;

    public void setViewModel(final BinaryTreeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new BinaryTreeViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void testCanInitEmptyModel() {
        BinaryTreeViewModel testViewModel = new BinaryTreeViewModel();
        assertEquals("", testViewModel.addNodeProperty().get());
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.addNodeProperty().get());
        assertEquals("", viewModel.remNodeProperty().get());
        assertEquals("", viewModel.getNodeProperty().get());
        assertEquals("", viewModel.searchProperty().get());
        assertEquals("", viewModel.searchResultProperty().get());
        assertEquals("", viewModel.sourceTreeProperty().get());
        assertEquals("", viewModel.getLogs());
        assertEquals("", viewModel.getLogsProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void testSearchResult() {
        viewModel.addNodeProperty().set("1");
        viewModel.addNodeToTree();
        viewModel.searchProperty().set("1");
        viewModel.searchNode();
        assertEquals("1", viewModel.getSearchResult());
        assertEquals(Status.ELEMENT_FOUND.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetBadFormatErrorFromAddNodeField() {
        viewModel.addNodeProperty().set("abc");
        viewModel.addNodeToTree();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testAddNodeToEmptyTree() {
        viewModel.addNodeProperty().set("24");
        viewModel.addNodeToTree();
        assertEquals(Status.ELEMENT_ADDED.toString(), viewModel.getStatus());
    }

    @Test
    public void testTryToSearchInEmptyTree() {
        viewModel.searchNode();
        assertEquals(Status.EMPTY_TREE.toString(), viewModel.getStatus());
    }

    @Test
    public void testTryToSearchBadInput() {
        viewModel.addNodeProperty().set("1");
        viewModel.addNodeToTree();
        viewModel.searchProperty().set("abc");
        viewModel.searchNode();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testTryToSearchValueThatNotExist() {
        viewModel.addNodeProperty().set("1");
        viewModel.addNodeToTree();
        viewModel.searchProperty().set("2");
        viewModel.searchNode();
        assertEquals(viewModel.getSearchResult(), "");
        assertEquals(Status.ELEMENT_NOT_FOUND.toString(), viewModel.getStatus());
    }

    @Test
    public void testRemoveNodeThatExists() {
        viewModel.addNodeProperty().set("1");
        viewModel.addNodeToTree();
        viewModel.remNodeProperty().set("1");
        viewModel.remNodeFromTree();
        assertEquals(Status.ELEMENT_DELETED.toString(), viewModel.getStatus());
    }

    @Test
    public void testRemoveNodeInEmptyTree() {
        viewModel.remNodeProperty().set("1");
        viewModel.remNodeFromTree();
        assertEquals(Status.EMPTY_TREE.toString(), viewModel.getStatus());
    }

    @Test
    public void testRemoveNodeThatNotExists() {
        viewModel.addNodeProperty().set("1");
        viewModel.addNodeToTree();
        viewModel.remNodeProperty().set("2");
        viewModel.remNodeFromTree();
        assertEquals(Status.ELEMENT_NOT_FOUND.toString(), viewModel.getStatus());
    }

    @Test
    public void testTryToRemoveNodeWithBadInput() {
        viewModel.addNodeProperty().set("3");
        viewModel.addNodeToTree();
        viewModel.remNodeProperty().set("abc");
        viewModel.remNodeFromTree();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetNodeInEmptyTree() {
        viewModel.getNodeProperty().set("5");
        viewModel.getNodeFromTree();
        assertEquals(Status.EMPTY_TREE.toString(), viewModel.getStatus());
    }

    @Test
    public void testTryToGetNodeWithBadInput() {
        viewModel.addNodeProperty().set("24");
        viewModel.addNodeToTree();
        viewModel.getNodeProperty().set("bcd");
        viewModel.getNodeFromTree();
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetNodeThatNotExists() {
        viewModel.addNodeProperty().set("21");
        viewModel.addNodeToTree();
        viewModel.getNodeProperty().set("3");
        viewModel.getNodeFromTree();
        assertEquals(Status.ELEMENT_NOT_FOUND.toString(), viewModel.getStatus());
    }

    @Test
    public void testGetNodeThatExists() {
        viewModel.addNodeProperty().set("17");
        viewModel.addNodeToTree();
        viewModel.getNodeProperty().set("17");
        viewModel.getNodeFromTree();
        assertEquals(Status.NODE_SUCCESSFUL_GOT.toString(), viewModel.getStatus());
    }

    @Test
    public void testPrintEmptyTree() {
        viewModel.printSourceTree();
        assertEquals(viewModel.getSourceTree(), "");
    }

    @Test
    public void testPrintTreeWithSingleElement() {
        viewModel.addNodeProperty().set("6");
        viewModel.addNodeToTree();
        viewModel.printSourceTree();
        assertEquals(viewModel.getSourceTree(), "6");
    }

    @Test
    public void testPrintTreeWithSeveralElements() {
        viewModel.addNodeProperty().set("40");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("20");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("10");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("30");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("60");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("50");
        viewModel.addNodeToTree();
        viewModel.addNodeProperty().set("70");
        viewModel.addNodeToTree();
        viewModel.printSourceTree();
        assertEquals(viewModel.getSourceTree(), "40,20,60,10,30,50,70");
    }


}
