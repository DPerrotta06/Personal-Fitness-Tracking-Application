package org.example.personalfitnesstracker.Controllers;

import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.example.personalfitnesstracker.DatabaseManagement.DatabaseManager;
import org.example.personalfitnesstracker.Factories.*;
import org.example.personalfitnesstracker.Models.BulkingGoal;
import org.example.personalfitnesstracker.Models.CardioGoal;
import org.example.personalfitnesstracker.Models.CuttingGoal;
import org.example.personalfitnesstracker.Models.Goal;
import org.example.personalfitnesstracker.Models.MuscularGoal;
import org.example.personalfitnesstracker.Models.User;

/**
 * Controller responsible for creating and managing different types of goals
 * (Bulking, Cutting, Cardio, Muscular).
 */
public class GoalController extends BaseController {

    private final BulkingGoalFactory bulkingGoalFactory;
    private final CuttingGoalFactory cuttingGoalFactory;
    private final CardioGoalFactory cardioGoalFactory;
    private final MuscularGoalFactory muscularGoalFactory;
    private final ObservableList<Goal> goals;

    /**
     * Constructor
     */
    public GoalController(User loggedUser) {
        super(loggedUser);
        this.bulkingGoalFactory = new BulkingGoalFactory();
        this.cuttingGoalFactory = new CuttingGoalFactory();
        this.cardioGoalFactory = new CardioGoalFactory();
        this.muscularGoalFactory = new MuscularGoalFactory();
        this.goals = FXCollections.observableArrayList();
    }

    /**
     *
     * @return
     */
    public ObservableList<Goal> getGoals() {
        return goals;
    }

    /**
     * Adding a bulking goal to the DB
     *
     * @param goalId
     * @param goalName
     * @param goalDescription
     * @param isCompleted
     * @param userId
     * @param targetWeightGain
     * @param targetDailyCaloricIntake
     * @return
     */
    public Goal addBulkingGoal(int goalId,
            String goalName,
            String goalDescription,
            boolean isCompleted,
            int userId,
            double targetWeightGain,
            int targetDailyCaloricIntake) {

        GoalAttributeData attr = new GoalAttributeData(
                goalId,
                goalName,
                goalDescription,
                isCompleted,
                userId,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                targetWeightGain,
                targetDailyCaloricIntake
        );

        Goal goal = bulkingGoalFactory.createNewGoal(attr);
        goals.add(goal);
        return goal;
    }

    /**
     * Adding a cutting goal to the DB
     *
     * @param goalId
     * @param goalName
     * @param goalDescription
     * @param isCompleted
     * @param userId
     * @param targetWeightLoss
     * @param targetCaloricDeficit
     * @return
     */
    public Goal addCuttingGoal(int goalId,
            String goalName,
            String goalDescription,
            boolean isCompleted,
            int userId,
            double targetWeightLoss,
            int targetCaloricDeficit) {

        GoalAttributeData attr = new GoalAttributeData(
                goalId,
                goalName,
                goalDescription,
                isCompleted,
                userId,
                null,
                null,
                null,
                null,
                null,
                targetWeightLoss,
                targetCaloricDeficit,
                null,
                null
        );

        Goal goal = cuttingGoalFactory.createNewGoal(attr);
        goals.add(goal);
        return goal;
    }

    /**
     * Adding a cardio goal to the DB
     *
     * @param goalId
     * @param goalName
     * @param goalDescription
     * @param isCompleted
     * @param userId
     * @param targetRestingHeartRate
     * @param maxDistance
     * @return
     */
    public Goal addCardioGoal(int goalId,
            String goalName,
            String goalDescription,
            boolean isCompleted,
            int userId,
            int targetRestingHeartRate,
            double maxDistance) {

        GoalAttributeData attr = new GoalAttributeData(
                goalId,
                goalName,
                goalDescription,
                isCompleted,
                userId,
                targetRestingHeartRate,
                maxDistance,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );

        Goal goal = cardioGoalFactory.createNewGoal(attr);
        goals.add(goal);
        return goal;
    }

    /**
     * Adding a muscular goal to the DB
     *
     * @param goalId
     * @param goalName
     * @param goalDescription
     * @param isCompleted
     * @param userId
     * @param heaviestLift
     * @param maxRepCount
     * @param maxSetsCount
     * @return
     */
    public Goal addMuscularGoal(int goalId,
            String goalName,
            String goalDescription,
            boolean isCompleted,
            int userId,
            int heaviestLift,
            int maxRepCount,
            int maxSetsCount) {

        GoalAttributeData attr = new GoalAttributeData(
                goalId,
                goalName,
                goalDescription,
                isCompleted,
                userId,
                null,
                null,
                heaviestLift,
                maxRepCount,
                maxSetsCount,
                null,
                null,
                null,
                null
        );

        Goal goal = muscularGoalFactory.createNewGoal(attr);
        goals.add(goal);
        return goal;
    }

    public ObservableList<Goal> filterByGoalType(String type) {
        ObservableList<Goal> goal = DatabaseManager.displayGoals(loggedUser.userIdProperty().get());
        List<Goal> filtered = goal.parallelStream().filter(g -> {
            if (type.equalsIgnoreCase("Muscular")) {
                return g instanceof MuscularGoal;
            } else if (type.equalsIgnoreCase("Cardio")) {
                return g instanceof CardioGoal;
            } else if (type.equalsIgnoreCase("Bulking")) {
                return g instanceof BulkingGoal;
            } else if (type.equalsIgnoreCase("Cutting")) {
                return g instanceof CuttingGoal;
            } else {
                showMessageWindow("Type unknown", "Please choose a valid workout type provided by the dropdown list to filter by.", Alert.AlertType.WARNING, ButtonType.OK);
                return false;
            }
        }).collect(Collectors.toList());
        return FXCollections.observableArrayList(filtered);
    }

}
