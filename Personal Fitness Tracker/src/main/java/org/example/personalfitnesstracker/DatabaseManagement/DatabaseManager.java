/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.personalfitnesstracker.DatabaseManagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.personalfitnesstracker.Models.*;

/**
 *
 * @author danie
 */
public class DatabaseManager { //might be immutable?

    private static final DBLoader loadDatabase = DBLoader.getInstance(); //reads all database credentials once
    private static final String URL = loadDatabase.getDbURL();
    private static final String USER = loadDatabase.getDbUser();
    private static final String PW = loadDatabase.getDbPassword();
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private final FileHandler logFile;

    public DatabaseManager() throws IOException {
        this.logFile = new FileHandler("src\\logfile.log", true);
        this.logFile.setFormatter(new SimpleFormatter());
        logger.addHandler(logFile);
    }

    /**
     * Checks text boxes filled in by the user and then it will go to the
     * database to see if that user already exists
     *
     * @param //emailBox
     * @param //passwordBox
     */
    public static boolean userExists(String email, byte[] hashedPw) {
        String query = "SELECT * FROM Users WHERE Email = ? AND Password = ?"; //prepared statement to act as a first line defense against SQL injections
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setString(1, email); //checks the email
            prepStat.setBytes(2, hashedPw); //checks the password
            ResultSet result = prepStat.executeQuery();
            if (result.next()) {
                return true; //user exists
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return false; //user doesn't exist
    }

    //====================================CREATE====================================
    /**
     *
     * @param user
     */
    public static void addNewUserToDb(User user) { //LOOK OVER
        String query = "INSERT INTO Users (Password, Email, Weight, Height, DateOfBirth, Username) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setBytes(1, user.passwordProperty().get()); //inserting password
            prepStat.setString(2, user.emailProperty().get()); //inserting email
            prepStat.setDouble(3, user.weightProperty().get()); //inserting weight
            prepStat.setDouble(4, user.heightProperty().get()); //inserting height
            prepStat.setDate(5, Date.valueOf(user.getDateOfBirth())); //inserting date of birth
            prepStat.setString(6, user.usernameProperty().get()); // inserting username
            prepStat.executeLargeUpdate(); //updates the table
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     * Logs a new sleep session from the view and controller and stores it into
     * the Sleep table in the database
     *
     * @param sleep
     */
    public static void addNewSleepSessionToDb(Sleep sleep) { //LOOK OVER
        String query = "INSERT INTO Sleep (SleepSessionID, UserID, SleepStart, SleepEnd) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setInt(1, sleep.userIdProperty().get());
            prepStat.setTimestamp(2, Timestamp.valueOf(sleep.sleepStartProperty()));
            prepStat.setTimestamp(3, Timestamp.valueOf(sleep.sleepEndProperty()));
            prepStat.executeLargeUpdate(); //updates the table
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param nutrition
     */
    public static void addNewNutritionToDb(Nutrition nutrition) {
        String query = "INSERT INTO Nutrition (NutritionDescription, TimeStamp, UserID) values (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, nutrition.nutritionDescriptionProperty().get());
            prepStat.setTimestamp(2, Timestamp.valueOf(nutrition.timeStampProperty()));
            prepStat.setInt(3, nutrition.userIdProperty().get());
            prepStat.executeUpdate(); //update parent table
            ResultSet parentRef = prepStat.getGeneratedKeys();
            if (parentRef.next()) {
                int nutritionId = parentRef.getInt(1);
                if (nutrition instanceof Food food) {
                    addFood(food, nutritionId, conn);
                } else if (nutrition instanceof Water water) {
                    addWater(water, nutritionId, conn);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param food
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addFood(Food food, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO Food (NutritionID, Recipe, Calories, Protein, Carbs, Fats, FoodServin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setString(2, food.recipeProperty().get());
            prepStat.setInt(3, food.caloriesProperty().get());
            prepStat.setInt(4, food.caloriesProperty().get());
            prepStat.setInt(5, food.proteinProperty().get());
            prepStat.setInt(6, food.carbsProperty().get());
            prepStat.setInt(7, food.fatsProperty().get());
            prepStat.setDouble(8, food.foodServingSizeProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param water
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addWater(Water water, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO Water (NutritionId, AmountInLiters) VALUES (?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setDouble(id, water.amountInLitersProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param goal
     */
    public static void addNewGoalToDb(Goal goal) {
        String query = "INSERT INTO Goal (GoalID, GoalName, GoalDescription, IsCompleted, UserID)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, goal.goalNameProperty().get());
            prepStat.setString(2, goal.goalDescriptionProperty().get());
            prepStat.setBoolean(3, goal.isCompletedProperty().get());
            prepStat.setInt(4, goal.userIdProperty().get());
            prepStat.executeUpdate();
            ResultSet parentRef = prepStat.getGeneratedKeys();
            if (parentRef.next()) {
                int goalId = parentRef.getInt(1);
                if (goal instanceof CardioGoal cardioGoal) {
                    addCardioGoal(cardioGoal, goalId, conn);
                } else if (goal instanceof MuscularGoal muscularGoal) {
                    addMuscularGoal(muscularGoal, goalId, conn);
                } else if (goal instanceof BulkingGoal bulkingGoal) {
                    addBulkingGoal((BulkingGoal) goal, goalId, conn);
                } else if (goal instanceof CuttingGoal cuttingGoal) {
                    addCuttingGoal(cuttingGoal, goalId, conn);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param cg
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addCardioGoal(CardioGoal cg, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO CardioGoal (GoalID, TargetRestingHeartRate, MaxDistance) VALUES (?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setInt(2, cg.targetRestingHeartRateProperty().get());
            prepStat.setDouble(3, cg.maxDistanceProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param mg
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addMuscularGoal(MuscularGoal mg, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO MuscularGoal (GoalID, HeaviestLift, MaxRepCount, MaxSetsGoal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setDouble(2, mg.heaviestLiftProperty().get());
            prepStat.setInt(3, mg.maxRepCountProperty().get());
            prepStat.setInt(4, mg.maxSetsCount().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param bg
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addBulkingGoal(BulkingGoal bg, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO BulkingGoal (GoalID, TargetWeightGain, TargetDailyCaloricDeficit) VALUES (?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setDouble(2, bg.targetWeightGainProperty().get());
            prepStat.setInt(3, bg.targetCaloricIntakeProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param cg
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addCuttingGoal(CuttingGoal cg, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO CuttingGoal (GoalID, TargetWeightLoss, TargetDailyCaloricDeficit) VALUES (?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setDouble(2, cg.targetWeightLossProperty().get());
            prepStat.setInt(3, cg.targetCaloricDeficitProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param workout
     */
    public static void addNewWorkoutToDb(Workout workout) {
        String query = "INSERT INTO Workouts (WorkoutName, WorkoutDescription, WorkoutDuration, CaloriesBurned, UserID, DateStamp) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepStat.setString(1, workout.workoutNameProperty().get());
            prepStat.setString(2, workout.workoutDescriptionProperty().get());
            prepStat.setDouble(3, workout.workoutDurationProperty().get());
            prepStat.setInt(4, workout.caloriesBurnedProperty().get());
            prepStat.setInt(5, workout.userIdProperty().get());
            prepStat.setTimestamp(6, Timestamp.valueOf(workout.dateStampProperty()));
            ResultSet parentRef = prepStat.getGeneratedKeys();
            if (parentRef.next()) {
                int workoutId = parentRef.getInt(1);
                if (workout instanceof MuscularWorkout muscularWorkout) {
                    addMuscularWorkout(muscularWorkout, workoutId, conn);
                } else if (workout instanceof CardioWorkout cardioWorkout) {
                    addCardioWorkout(cardioWorkout, workoutId, conn);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param mw
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addMuscularWorkout(MuscularWorkout mw, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO MuscularWorkout (WorkoutID, TotalSets, TotalReps, TotalWeight) VALUES (?, ?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setInt(2, mw.totalSetsProperty().get());
            prepStat.setInt(3, mw.totalRepsProperty().get());
            prepStat.setDouble(4, mw.totalWeightProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param cw
     * @param id
     * @param conn
     * @throws SQLException
     */
    private static void addCardioWorkout(CardioWorkout cw, int id, Connection conn) throws SQLException {
        String query = "INSERT INTO CardioWorkout (WorkoutID, TotalDistance, HeartRateZone) VALUES (?, ?, ?)";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, id);
            prepStat.setDouble(2, cw.totalDistanceProperty().get());
            prepStat.setString(3, cw.heartRateZoneProperty().get());
            prepStat.executeUpdate();
        }
    }

    public static ObservableList<User> getUserById() {
        ObservableList<User> user = FXCollections.observableArrayList();
        String query = "SELECT * FROM Users WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW)) {
            PreparedStatement prepStat = conn.prepareStatement(query);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                user.add(new User(
                        set.getInt("UserID"),
                        set.getBytes("Password"),
                        set.getString("Email"),
                        set.getDouble("Weight"),
                        set.getDouble("Height"),
                        set.getDate("DateOfBirth").toLocalDate(),
                        set.getString("Username")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return user;
    }

    //====================================READ====================================
    /**
     * Retrieve the right user via their email
     *
     * @return
     */
    public static ObservableList<User> getUserByEmail(String email) {
        ObservableList<User> user = FXCollections.observableArrayList();
        String query = "SELECT * FROM Users WHERE Email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW)) {
            PreparedStatement prepStat = conn.prepareStatement(query);
            prepStat.setString(1, email);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                user.add(new User(
                        set.getInt("UserID"),
                        set.getBytes("Password"),
                        set.getString("Email"),
                        set.getDouble("Weight"),
                        set.getDouble("Height"),
                        set.getDate("DateOfBirth").toLocalDate(),
                        set.getString("Username")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return user;
    }

    /**
     * Retrieving all data from the Sleep table WORK IN PROGRESS
     *
     * @return
     */
    public static ObservableList<Sleep> displaySleepingLogs(int userID) {
        ObservableList<Sleep> sleep = FXCollections.observableArrayList();
        String query = "SELECT * FROM Sleep WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, userID);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                sleep.add(new Sleep(
                        set.getInt("SleepSessionID"),
                        set.getTimestamp("SleepStart").toLocalDateTime(),
                        set.getTimestamp("SleepEnd").toLocalDateTime(),
                        set.getInt("UserID")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return sleep;
    }

    /**
     * Retrieving all data from the Nutrition table and its children
     *
     * @return
     */
    public static ObservableList<Nutrition> displayNutritionLogs(int userID) {
        ObservableList<Nutrition> nutrition = FXCollections.observableArrayList();
        String query = "SELECT n.*, f.Recipe, f.Calories, f.Protein, f.Carbs, f.Fats, f.FoodServing, w.AmountInLiters "
                + "FROM Nutrition n LEFT JOIN Food f ON n.NutritionID = f.Nutrition.ID "
                + "LEFT JOIN Water w ON n.NutritionID = w.NutritionID "
                + "WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, userID);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                if (set.getObject("Calories") != null) {
                    nutrition.add(new Food(
                            set.getInt("NutritionID"),
                            set.getString("NutritionDescription"),
                            set.getTimestamp("TimeStamp").toLocalDateTime(),
                            set.getInt("UserID"),
                            set.getString("Recipe"),
                            set.getInt("Calories"),
                            set.getInt("Protein"),
                            set.getInt("Carbs"),
                            set.getInt("Fats"),
                            set.getDouble("FoodServing")
                    ));
                } else {
                    nutrition.add(new Water(
                            set.getInt("NutritionID"),
                            set.getString("NutritionDescription"),
                            set.getTimestamp("TimeStamp").toLocalDateTime(),
                            set.getInt("UserID"),
                            set.getDouble("AmountInLiters")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return nutrition;
    }

    /**
     * Retrieving all data from the Workouts table and all its children
     *
     * @return
     */
    public static ObservableList<Workout> displayWorkoutLogs(int userID) {
        ObservableList<Workout> workout = FXCollections.observableArrayList();
        String query = "SELECT w.*, m.TotalSets, m.TotalReps, m.TotalWeight, c.TotalDistance, c.HeartRateZone "
                + "FROM Workouts w LEFT JOIN MuscularWorkout m ON w.WorkoutID = m.WorkoutID "
                + "LEFT JOIN CardioWorkout c ON w.WorkoutID = c.WorkoutID "
                + "WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, userID);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                if (set.getObject("TotalReps") != null) {
                    workout.add(new MuscularWorkout(
                            set.getInt("WorkoutID"),
                            set.getString("WorkoutName"),
                            set.getString("WorkoutDescription"),
                            set.getDouble("WorkoutDuration"),
                            set.getInt("CaloriesBurned"),
                            set.getInt("UserID"),
                            set.getTimestamp("DateStamp").toLocalDateTime(),
                            set.getInt("TotalSets"),
                            set.getInt("TotalReps"),
                            set.getDouble("TotalWeight")
                    ));
                } else {
                    workout.add(new CardioWorkout(
                            set.getInt("WorkoutID"),
                            set.getString("WorkoutName"),
                            set.getString("WorkoutDescription"),
                            set.getDouble("WorkoutDuration"),
                            set.getInt("CaloriesBurned"),
                            set.getInt("UserID"),
                            set.getTimestamp("DateStamp").toLocalDateTime(),
                            set.getDouble("TotalDistance"),
                            set.getString("HeartRateZone")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return workout;
    }

    /**
     * Retrieving all data from the Goal table and all its children
     *
     * @return
     */
    public static ObservableList<Goal> displayGoals(int userID) {
        ObservableList<Goal> goal = FXCollections.observableArrayList();
        String query = "SELECT g.*, m.HeaviestLift, m.MaxRepCount, m.MaxSetsGoal, ca.TargetRestingHeartRate, ca.MaxDistance, b.TargetWeightGain, b.TargetDailyCaloricIntake, cu.TargetWeightLoss, cu.TargetDailyCaloricDeficit"
                + "FROM Goal g LEFT JOIN MuscularGoal m ON g.WorkoutID = m.WorkoutID "
                + "LEFT JOIN CardioGoal ca ON g.WorkoutID = ca.WorkoutID "
                + "LEFT JOIN BulkingGoal b ON w.WorkoutID = b.WorkoutID "
                + "LEFT JOIN CuttingGoal cu ON w.WorkoutID = cu.WorkoutID "
                + "WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, userID);
            ResultSet set = prepStat.executeQuery();
            while (set.next()) {
                if (set.getObject("MaxRepCount") != null) {
                    goal.add(new MuscularGoal(
                            set.getInt("WorkoutID"),
                            set.getString("GoalName"),
                            set.getString("GoalDescription"),
                            set.getBoolean("IsCompleted"),
                            set.getInt("UserID"),
                            set.getDouble("HeaviestLift"),
                            set.getInt("MaxRepCount"),
                            set.getInt("MaxSetsGoal")
                    ));
                } else if (set.getObject("TargetRestingHeartRate") != null) {
                    goal.add(new CardioGoal(
                            set.getInt("WorkoutID"),
                            set.getString("GoalName"),
                            set.getString("GoalDescription"),
                            set.getBoolean("IsCompleted"),
                            set.getInt("UserID"),
                            set.getInt("TargetRestingHeartRate"),
                            set.getDouble("MaxDistance")
                    ));
                } else if (set.getObject("TargetWeightGain") != null) {
                    goal.add(new BulkingGoal(
                            set.getInt("WorkoutID"),
                            set.getString("GoalName"),
                            set.getString("GoalDescription"),
                            set.getBoolean("IsCompleted"),
                            set.getInt("UserID"),
                            set.getDouble("TargetWeightGain"),
                            set.getInt("TargetDailyCaloricIntake")
                    ));
                } else {
                    goal.add(new CuttingGoal(
                            set.getInt("WorkoutID"),
                            set.getString("GoalName"),
                            set.getString("GoalDescription"),
                            set.getBoolean("IsCompleted"),
                            set.getInt("UserID"),
                            set.getDouble("TargetWeightLoss"),
                            set.getInt("TargetDailyCaloricDeficit")
                    ));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
        return goal;
    }

    //====================================UPDATE====================================
    /**
     *
     * @param user
     * @return
     */
    public static void updateUser(User user) {
        String query = "UPDATE Users SET Password = ?, Email = ?, Weight = ?, Height = ?, DateOfBirth = ?, Username = ? WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setBytes(1, user.passwordProperty().get());
            prepStat.setString(2, user.emailProperty().get());
            prepStat.setDouble(3, user.weightProperty().get());
            prepStat.setDouble(4, user.heightProperty().get());
            prepStat.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param sleep
     * @return
     */
    public static void updateSleep(Sleep sleep) {
        String query = "UPDATE Sleep SET SleepStart = ?, SleepEnd = ? WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setTimestamp(1, Timestamp.valueOf(sleep.sleepStartProperty()));
            prepStat.setTimestamp(2, Timestamp.valueOf(sleep.sleepEndProperty()));
            prepStat.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param goal
     */
    public static void updateGoal(Goal goal) {
        String query = "UPDATE Goal SET GoalName = ?, GoalDescription = ?, IsCompleted = ?, WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setString(1, goal.goalNameProperty().get());
            prepStat.setString(2, goal.goalDescriptionProperty().get());
            prepStat.setBoolean(3, goal.isCompletedProperty().get());
            prepStat.executeUpdate();
            if (goal instanceof MuscularGoal mg) {
                updateMuscularGoal(mg, conn);
            } else if (goal instanceof CardioGoal cg) {
                updateCardioGoal(cg, conn);
            } else if (goal instanceof BulkingGoal bg) {
                updateBulkingGoal(bg, conn);
            } else if (goal instanceof CuttingGoal cu) {
                updateCuttingGoal(cu, conn);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param mg
     * @param conn
     * @throws SQLException
     */
    private static void updateMuscularGoal(MuscularGoal mg, Connection conn) throws SQLException {
        String query = "UPDATE MuscularGoal SET HeaviestLift = ?, MaxRepCount = ?, MaxSetsGoal = ? WHERE GoalID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setDouble(1, mg.heaviestLiftProperty().get());
            prepStat.setInt(2, mg.maxRepCountProperty().get());
            prepStat.setInt(3, mg.maxSetsCount().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param cg
     * @param conn
     * @throws SQLException
     */
    private static void updateCardioGoal(CardioGoal cg, Connection conn) throws SQLException {
        String query = "UPDATE CardioGoal SET TargetRestingHeartRate = ?, MaxDistance = ? WHERE GoalID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, cg.targetRestingHeartRateProperty().get());
            prepStat.setDouble(2, cg.maxDistanceProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param bg
     * @param conn
     * @throws SQLException
     */
    private static void updateBulkingGoal(BulkingGoal bg, Connection conn) throws SQLException {
        String query = "UPDATE BulkingGoal SET TargetWeightGain = ?, TargetDailyCaloricIntake = ? WHERE GoalID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setDouble(1, bg.targetWeightGainProperty().get());
            prepStat.setInt(2, bg.targetCaloricIntakeProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param cu
     * @param conn
     * @throws SQLException
     */
    private static void updateCuttingGoal(CuttingGoal cu, Connection conn) throws SQLException {
        String query = "UPDATE CuttingGoal SET TargetWeightLoss = ?, TargetDailyCaloricDeficit = ? WHERE UserID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setDouble(1, cu.targetWeightLossProperty().get());
            prepStat.setInt(2, cu.targetCaloricDeficitProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param workout
     * @return
     */
    public static void updateWorkout(Workout workout) {
        String query = "UPDATE Workouts SET WorkoutName = ?, WorkoutDescription = ?, WorkoutDuration = ?, CaloriesBurned = ?, DateStamp = ? WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setString(1, workout.workoutNameProperty().get());
            prepStat.setString(2, workout.workoutDescriptionProperty().get());
            prepStat.setInt(3, workout.caloriesBurnedProperty().get());
            prepStat.setTimestamp(4, Timestamp.valueOf(workout.dateStampProperty()));
            prepStat.executeUpdate();
            if (workout instanceof MuscularWorkout mw) {
                updateMuscularWorkout(mw, conn);
            } else if (workout instanceof CardioWorkout cw) {
                updateCardioWorkout(cw, conn);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param mw
     * @param conn
     * @throws SQLException
     */
    private static void updateMuscularWorkout(MuscularWorkout mw, Connection conn) throws SQLException {
        String query = "UPDATE MuscularWorkout SET TotalSets = ?, TotalReps = ?, TotalWeight = ? WHERE WorkoutID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setInt(1, mw.totalSetsProperty().get());
            prepStat.setInt(2, mw.totalRepsProperty().get());
            prepStat.setDouble(3, mw.totalWeightProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param cw
     * @param conn
     * @throws SQLException
     */
    private static void updateCardioWorkout(CardioWorkout cw, Connection conn) throws SQLException {
        String query = "UPDATE CardioWorkout SET TotalDistance = ?, HeartRateZone = ? WHERE WorkoutID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setDouble(1, cw.totalDistanceProperty().get());
            prepStat.setString(2, cw.heartRateZoneProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param nutrition
     */
    public static void updateNutrition(Nutrition nutrition) {
        String query = "UPDATE Nutrition SET NutritionDescription = ?, TimeStamp = ? WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setString(1, nutrition.nutritionDescriptionProperty().get());
            prepStat.setTimestamp(2, Timestamp.valueOf(nutrition.timeStampProperty()));
            prepStat.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error connecting to database.", e); //logger
        }
    }

    /**
     *
     * @param food
     * @param conn
     * @throws SQLException
     */
    private static void updateFood(Food food, Connection conn) throws SQLException {
        String query = "UPDATE Food SET Recipe = ?, Calories = ?, Protein = ?, Carbs = ?, Fats = ?, FoodServing = ? WHERE NutritionID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setString(1, food.recipeProperty().get());
            prepStat.setInt(2, food.caloriesProperty().get());
            prepStat.setInt(3, food.proteinProperty().get());
            prepStat.setInt(4, food.carbsProperty().get());
            prepStat.setInt(5, food.fatsProperty().get());
            prepStat.setDouble(6, food.foodServingSizeProperty().get());
            prepStat.executeUpdate();
        }
    }

    /**
     *
     * @param water
     * @param conn
     * @throws SQLException
     */
    private static void updateWater(Water water, Connection conn) throws SQLException {
        String query = "UPDATE Water SET AmountInLiters = ? WHERE NutritionID = ?";
        try (PreparedStatement prepStat = conn.prepareStatement(query)) {
            prepStat.setDouble(1, water.amountInLitersProperty().get());
            prepStat.executeUpdate();
        }
    }

    //====================================DELETE====================================
    /**
     *
     * @param userId
     */
    public static void deleteUser(int userId) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PW); PreparedStatement prepStat = conn.prepareStatement(query)) {

            prepStat.setInt(1, userId);
            prepStat.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error deleting user.", e);
        }
    }

    /**
     * REMOVE THIS LATER ONLY USE IS TO TEST CONNECTION!!! DELETE AT
     * PRODUCTION!!!
     *
     * @return
     */
    public static boolean testConnection() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PW)) {
            System.out.println("Connection successful");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection failed");
            return false;
        }
    }
}
