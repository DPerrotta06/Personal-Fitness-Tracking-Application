create database FitnessTrackerDB;
use FitnessTrackerDB;

create table Users( 
    UserID int AUTO_INCREMENT primary key not null,
    Password varbinary(32) not null,
    Email varchar(255) unique not null,
    Weight decimal(10,2) not null,
    Height decimal(10,2) not null,
    DateOfBirth date not null
);

create table Sleep(
    SleepSessionID int AUTO_INCREMENT primary key not null,
    SleepStart datetime not null,
    SleepEnd datetime not null,
    UserID int,
    constraint fk_sleep_user_id foreign key (UserID) references Users(UserID)
);

create table Goal(
    GoalID int AUTO_INCREMENT primary key not null,
    GoalName varchar(25) not null,
    GoalDescription varchar(100),
    IsCompleted boolean,
    UserID int,
    constraint fk_goal_user_id foreign key (UserID) references Users(UserID)
);

create table MuscularGoal(
    GoalID int primary key not null,
    HeaviestLift decimal(10,2),
    MaxRepCount int not null,
    MaxSetsGoal int not null,
    constraint fk_muscular_goal_id foreign key (GoalID) references Goal(GoalID) on delete cascade
);

create table CardioGoal(
    GoalID int primary key not null,
    TargetRestingHeartRate int not null,
    MaxDistance decimal(10,2),
    constraint fk_cardio_goal_id foreign key (GoalID) references Goal(GoalID) on delete cascade
);

create table BulkingGoal(
    GoalID int primary key not null,
    TargetWeightGain decimal(10,2) not null,
    TargetDailyCaloricIntake int not null,
    constraint fk_bulking_goal_id foreign key (GoalID) references Goal(GoalID) on delete cascade
);

create table CuttingGoal(
    GoalID int primary key not null,
    TargetWeightLoss decimal(10,2) not null,
    TargetDailyCaloricDeficit int not null,
    constraint fk_cutting_goal_id foreign key (GoalID) references Goal(GoalID) on delete cascade
);

create table Workouts(
    WorkoutID int AUTO_INCREMENT primary key not null,
    WorkoutName varchar(32) not null,
    WorkoutDescription varchar(100),
    WorkoutDuration int not null,
    CaloriesBurned int not null,
    UserID int not null,
    DateStamp date not null,
    constraint fk_workout_user_id foreign key (UserID) references Users(UserID)
);

create table MuscularWorkout(
    WorkoutID int primary key not null,
    TotalSets int not null,
    TotalReps int not null,
    TotalWeight decimal(10,2),
    constraint fk_muscular_workout_id foreign key (WorkoutID) references Workouts(WorkoutID) on delete cascade
);

create table CardioWorkout(
    WorkoutID int primary key not null,
    TotalDistance decimal(10,2),
    HeartRateZone varchar(10) not null,
    constraint fk_cardio_workout_id foreign key (WorkoutID) references Workouts(WorkoutID) on delete cascade
);

create table Nutrition(
    NutritionID int AUTO_INCREMENT primary key not null,
    NutritionDescription varchar(250) not null,
    TimeStamp datetime not null,
    UserID int not null,
    constraint fk_nutrition_user_id foreign key (UserID) references Users(UserID)
);

create table Water(
    NutritionID int primary key not null,
    AmountInLiters decimal(10,2) not null,
    constraint fk_water_nutrition_id foreign key (NutritionID) references Nutrition(NutritionID) on delete cascade
);

create table Food(
    NutritionID int primary key not null,
    Recipe varchar(150),
    Calories int not null,
    Protein int not null,
    Carbs int not null,
    Fats int not null,
    FoodServing decimal(10,2) not null,
    constraint fk_food_nutrition_id foreign key (NutritionID) references Nutrition(NutritionID) on delete cascade
);

alter table Users add Username varchar(24);
alter table Sleep drop column SleepStart;
alter table Sleep drop column SleepEnd;
alter table Sleep add SleepStart TimeStamp;
alter table Sleep add column SleepEnd TimeStamp null;
alter table Workouts modify WorkoutDuration decimal(10,2);