/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.cgro.votemanager.util;

import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.util.Callback;
import net.cgro.votemanager.model.CheckResult;
import net.cgro.votemanager.model.ResultType;

/**
 * @author fabian
 */
public class CheckResultCellFactory implements Callback<TableColumn, TableCell> {

    @Override
    public TableCell call(TableColumn p) {

        TableCell cell = new TableCell<CheckResult, Object>() {
            @Override
            public void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : getString());
                setGraphic(null);
                TableRow currentRow = getTableRow();
                CheckResult result = currentRow == null ? null : (CheckResult) currentRow.getItem();
                if (result != null) {
                    clearPriorityStyle();
                    setPriorityStyle(result.getType());
                }
            }

            @Override
            public void updateSelected(boolean upd) {
                super.updateSelected(upd);
            }

            private void clearPriorityStyle() {
                ObservableList<String> styleClasses = getStyleClass();
                styleClasses.remove("styleOK");
                styleClasses.remove("styleWARNING");
                styleClasses.remove("styleERROR");
            }

            private void setPriorityStyle(ResultType type) {
                switch (type) {
                    case RESULT_TYPE_OK:
                        getStyleClass().add("styleOK");
                        break;
                    case RESULT_TYPE_WARNING:
                        getStyleClass().add("styleWARNING");
                        break;
                    case RESULT_TYPE_ERROR:
                        getStyleClass().add("styleERROR");
                        break;
                }
            }

            private String getString() {
                return getItem() == null ? "" : getItem().toString();
            }
        };
        return cell;
    }
}
