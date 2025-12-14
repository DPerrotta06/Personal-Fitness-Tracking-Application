package org.example.personalfitnesstracker.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Models.Food;
import org.example.personalfitnesstracker.Models.User;
import org.example.personalfitnesstracker.Models.Water;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class NutritionController extends BaseController {

    // ========================================================================
    // FXML FIELDS — WATER TAB
    // ========================================================================
    @FXML private TableView<Water> waterTable;
    @FXML private TableColumn<Water, Integer> waterNutritionID;
    @FXML private TableColumn<Water, String>  waterNutriDesc;
    @FXML private TableColumn<Water, String>  waterDate;
    @FXML private TableColumn<Water, Double>  amtDrank;

    @FXML private Button waterBackButton;
    @FXML private ComboBox<String> waterComboBox;

    // WATER INPUT FIELDS
    @FXML private TextField waterDescField;
    @FXML private TextField waterAmountField;
    @FXML private Button addWaterButton;

    // ========================================================================
    // FXML FIELDS — FOOD TAB
    // ========================================================================
    @FXML private TableView<Food> foodTable;
    @FXML private TableColumn<Food, Integer> foodNutritionId;
    @FXML private TableColumn<Food, String>  foodNutriDesc;
    @FXML private TableColumn<Food, String>  foodNutriDate;
    @FXML private TableColumn<Food, String>  foodRecipe;
    @FXML private TableColumn<Food, Integer> foodCal;
    @FXML private TableColumn<Food, Integer> foodPro;
    @FXML private TableColumn<Food, Integer> foodCarb;
    @FXML private TableColumn<Food, Integer> foodFat;
    @FXML private TableColumn<Food, Double>  foodServing;

    @FXML private Button foodBackButton;
    @FXML private ComboBox<String> filterFoodComboBox;

    // FOOD INPUT FIELDS
    @FXML private TextField foodDescField;
    @FXML private TextField foodRecipeField;
    @FXML private TextField foodCalField;
    @FXML private TextField foodProField;
    @FXML private TextField foodCarbField;
    @FXML private TextField foodFatField;
    @FXML private TextField foodServingField;
    @FXML private Button addFoodButton;


    // ========================================================================
    // STATE
    // ========================================================================
    private User currentUser;

    private final ObservableList<Water> allWater = FXCollections.observableArrayList();
    private final ObservableList<Food>  allFood  = FXCollections.observableArrayList();

    private final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // ========================================================================
    // INITIALIZE
    // ========================================================================
    @FXML
    private void initialize() {
        setupWaterTable();
        setupFoodTable();
        setupFilters();
        setupBackButtons();
        setupContextMenus();
        setupAddButtons();
    }

    public void setUser(User user) {
        this.currentUser = user;
        refreshAll();
    }


    // ========================================================================
    // TABLE SETUP
    // ========================================================================
    private void setupWaterTable() {
        waterNutritionID.setCellValueFactory(
                data -> data.getValue().nutritionIdProperty().asObject());
        waterNutriDesc.setCellValueFactory(
                data -> data.getValue().nutritionDescriptionProperty());
        waterDate.setCellValueFactory(
                data -> javafx.beans.binding.Bindings.createStringBinding(
                        () -> data.getValue().timeStampProperty().format(dateFormatter)));
        amtDrank.setCellValueFactory(
                data -> data.getValue().amountInLitersProperty().asObject());

        waterTable.setItems(allWater);
    }

    private void setupFoodTable() {
        foodNutritionId.setCellValueFactory(
                data -> data.getValue().nutritionIdProperty().asObject());
        foodNutriDesc.setCellValueFactory(
                data -> data.getValue().nutritionDescriptionProperty());
        foodNutriDate.setCellValueFactory(
                data -> javafx.beans.binding.Bindings.createStringBinding(
                        () -> data.getValue().timeStampProperty().format(dateFormatter)));
        foodRecipe.setCellValueFactory(cell -> cell.getValue().recipeProperty());
        foodCal.setCellValueFactory(cell -> cell.getValue().caloriesProperty().asObject());
        foodPro.setCellValueFactory(cell -> cell.getValue().proteinProperty().asObject());
        foodCarb.setCellValueFactory(cell -> cell.getValue().carbsProperty().asObject());
        foodFat.setCellValueFactory(cell -> cell.getValue().fatsProperty().asObject());
        foodServing.setCellValueFactory(cell -> cell.getValue().foodServingSizeProperty().asObject());

        foodTable.setItems(allFood);
    }


    // ========================================================================
    // FILTERS
    // ========================================================================
    private void setupFilters() {
        waterComboBox.getItems().setAll("All", "Today", "Last 7 days");
        waterComboBox.setValue("All");
        waterComboBox.setOnAction(e -> applyWaterFilter());

        filterFoodComboBox.getItems().setAll("All", "Today", "Last 7 days");
        filterFoodComboBox.setValue("All");
        filterFoodComboBox.setOnAction(e -> applyFoodFilter());
    }

    private void applyWaterFilter() {
        FilteredList<Water> filtered = new FilteredList<>(allWater, w -> true);

        LocalDate today = LocalDate.now();
        String sel = waterComboBox.getValue();

        if ("Today".equals(sel)) {
            filtered.setPredicate(w -> w.timeStampProperty().toLocalDate().isEqual(today));
        } else if ("Last 7 days".equals(sel)) {
            LocalDate weekAgo = today.minusDays(7);
            filtered.setPredicate(w -> !w.timeStampProperty().toLocalDate().isBefore(weekAgo));
        }

        waterTable.setItems(filtered);
    }

    private void applyFoodFilter() {
        FilteredList<Food> filtered = new FilteredList<>(allFood, f -> true);

        LocalDate today = LocalDate.now();
        String sel = filterFoodComboBox.getValue();

        if ("Today".equals(sel)) {
            filtered.setPredicate(f -> f.timeStampProperty().toLocalDate().isEqual(today));
        } else if ("Last 7 days".equals(sel)) {
            LocalDate weekAgo = today.minusDays(7);
            filtered.setPredicate(f -> !f.timeStampProperty().toLocalDate().isBefore(weekAgo));
        }

        foodTable.setItems(filtered);
    }


    // ========================================================================
    // BACK BUTTON
    // ========================================================================
    private void setupBackButtons() {
        waterBackButton.setOnAction(e -> closeWindow());
        foodBackButton.setOnAction(e -> closeWindow());
    }

    private void closeWindow() {
        Stage stage = (Stage) waterBackButton.getScene().getWindow();
        stage.close();
    }


    // ========================================================================
    // ADD BUTTONS
    // ========================================================================
    private void setupAddButtons() {
        addWaterButton.setOnAction(e -> handleAddWater());
        addFoodButton.setOnAction(e -> handleAddFood());
    }

    private void handleAddWater() {
        if (currentUser == null) {
            showError("No user loaded.");
            return;
        }

        String desc = waterDescField.getText().trim();
        String amountStr = waterAmountField.getText().trim();

        if (desc.isEmpty() || amountStr.isEmpty()) {
            showError("Please fill all fields.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (Exception ex) {
            showError("Amount must be a number.");
            return;
        }

        Water w = new Water(
                0,
                desc,
                LocalDateTime.now(),
                currentUser.userIdProperty().get(),
                amount
        );

        DatabaseManager.addNewNutritionToDb(w);
        refreshAll();

        waterDescField.clear();
        waterAmountField.clear();
    }

    private void handleAddFood() {
        if (currentUser == null) {
            showError("No user loaded.");
            return;
        }

        try {
            Food food = new Food(
                    0,
                    foodDescField.getText(),
                    LocalDateTime.now(),
                    currentUser.userIdProperty().get(),
                    foodRecipeField.getText(),
                    Integer.parseInt(foodCalField.getText()),
                    Integer.parseInt(foodProField.getText()),
                    Integer.parseInt(foodCarbField.getText()),
                    Integer.parseInt(foodFatField.getText()),
                    Double.parseDouble(foodServingField.getText())
            );

            DatabaseManager.addNewNutritionToDb(food);
            refreshAll();

            foodDescField.clear();
            foodRecipeField.clear();
            foodCalField.clear();
            foodProField.clear();
            foodCarbField.clear();
            foodFatField.clear();
            foodServingField.clear();

        } catch (Exception ex) {
            showError("Invalid input. Check all fields.");
        }
    }


    // ========================================================================
    // CONTEXT MENU (EDIT / DELETE)
    // ========================================================================
    private void setupContextMenus() {

        // WATER MENU
        ContextMenu waterMenu = new ContextMenu();
        MenuItem editWater = new MenuItem("Edit");
        MenuItem deleteWater = new MenuItem("Delete");

        editWater.setOnAction(e -> editSelectedWater());
        deleteWater.setOnAction(e -> deleteSelectedWater());

        waterMenu.getItems().addAll(editWater, deleteWater);
        waterTable.setContextMenu(waterMenu);


        // FOOD MENU
        ContextMenu foodMenu = new ContextMenu();
        MenuItem editFood = new MenuItem("Edit");
        MenuItem deleteFood = new MenuItem("Delete");

        editFood.setOnAction(e -> editSelectedFood());
        deleteFood.setOnAction(e -> deleteSelectedFood());

        foodMenu.getItems().addAll(editFood, deleteFood);
        foodTable.setContextMenu(foodMenu);
    }


    // ========================================================================
    // LOAD DATA
    // ========================================================================
    private void refreshAll() {
        loadWater();
        loadFood();
    }

    private void loadWater() {
        allWater.setAll(DatabaseManager.getAllWater(currentUser.userIdProperty().get()));
        applyWaterFilter();
    }

    private void loadFood() {
        allFood.setAll(DatabaseManager.getAllFood(currentUser.userIdProperty().get()));
        applyFoodFilter();
    }


    // ========================================================================
    // EDIT / DELETE WATER
    // ========================================================================
    private void editSelectedWater() {
        Water selected = waterTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a water entry to edit.");
            return;
        }

        String newDesc = askText("Edit Description", "Edit:", selected.nutritionDescriptionProperty().get());
        if (newDesc == null) return;

        String newLitersStr = askText("Edit Amount", "Liters:", String.valueOf(selected.amountInLitersProperty().get()));
        if (newLitersStr == null) return;

        try {
            double newLiters = Double.parseDouble(newLitersStr.trim());
            selected.nutritionDescriptionProperty().set(newDesc);
            selected.amountInLitersProperty().set(newLiters);

            DatabaseManager.updateNutrition(selected);
            refreshAll();

        } catch (Exception ex) {
            showError("Invalid number input.");
        }
    }

    private void deleteSelectedWater() {
        Water selected = waterTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a water entry to delete.");
            return;
        }

        if (!confirm("Delete Entry", "Are you sure?")) return;

        DatabaseManager.deleteNutrition(selected.nutritionIdProperty().get());
        refreshAll();
    }


    // ========================================================================
    // EDIT / DELETE FOOD
    // ========================================================================
    private void editSelectedFood() {
        Food selected = foodTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a food entry to edit.");
            return;
        }

        String desc = askText("Edit Description", "Edit:", selected.nutritionDescriptionProperty().get());
        if (desc == null) return;

        String recipe = askText("Edit Recipe", "Edit:", selected.recipeProperty().get());
        if (recipe == null) return;

        Integer calories = askInt("Edit Calories", "Calories:", selected.caloriesProperty().get());
        if (calories == null) return;

        Integer protein = askInt("Edit Protein", "Protein:", selected.proteinProperty().get());
        if (protein == null) return;

        Integer carbs = askInt("Edit Carbs", "Carbs:", selected.carbsProperty().get());
        if (carbs == null) return;

        Integer fats = askInt("Edit Fats", "Fats:", selected.fatsProperty().get());
        if (fats == null) return;

        Double serving = askDouble("Edit Serving", "Serving size:", selected.foodServingSizeProperty().get());
        if (serving == null) return;

        selected.nutritionDescriptionProperty().set(desc);
        selected.recipeProperty().set(recipe);
        selected.caloriesProperty().set(calories);
        selected.proteinProperty().set(protein);
        selected.carbsProperty().set(carbs);
        selected.fatsProperty().set(fats);
        selected.foodServingSizeProperty().set(serving);

        DatabaseManager.updateNutrition(selected);
        refreshAll();
    }

    private void deleteSelectedFood() {
        Food selected = foodTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Select a food entry to delete.");
            return;
        }

        if (!confirm("Delete Entry", "Are you sure?")) return;

        DatabaseManager.deleteNutrition(selected.nutritionIdProperty().get());
        refreshAll();
    }


    // ========================================================================
    // INPUT HELPERS
    // ========================================================================
    private String askText(String title, String header, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(null);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private Integer askInt(String title, String header, int defaultValue) {
        String txt = askText(title, header, String.valueOf(defaultValue));
        if (txt == null) return null;
        try { return Integer.parseInt(txt.trim()); }
        catch (Exception e) { showError("Must be an integer."); return null; }
    }

    private Double askDouble(String title, String header, double defaultValue) {
        String txt = askText(title, header, String.valueOf(defaultValue));
        if (txt == null) return null;
        try { return Double.parseDouble(txt.trim()); }
        catch (Exception e) { showError("Must be a number."); return null; }
    }

    private void showError(String msg) {
        showMessageWindow("Error", msg, Alert.AlertType.ERROR, ButtonType.OK);
    }

    private boolean confirm(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL);
        alert.setHeaderText(null);
        return alert.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }
}
