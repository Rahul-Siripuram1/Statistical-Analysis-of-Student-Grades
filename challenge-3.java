import java.util.ArrayList;
import java.util.List;

public class GradesStatistics {

    public static class Student {
        String name;
        List<SubjectGrade> grades;

        public Student(String name, List<SubjectGrade> grades) {
            this.name = name;
            this.grades = grades;
        }
    }

    public static class SubjectGrade {
        String subject;
        int grade;

        public SubjectGrade(String subject, int grade) {
            this.subject = subject;
            this.grade = grade;
        }
    }

    public static class StatisticsResult {
        List<Double> averageGrades;
        List<Double> averageSubjects;
        double overallAverage;
        double stdDeviation;

        public StatisticsResult(List<Double> averageGrades, List<Double> averageSubjects, double overallAverage, double stdDeviation) {
            this.averageGrades = averageGrades;
            this.averageSubjects = averageSubjects;
            this.overallAverage = overallAverage;
            this.stdDeviation = stdDeviation;
        }
    }

    public static StatisticsResult calculateGradesStatistics(List<Student> students) {
        int numStudents = students.size();
        int numSubjects = students.get(0).grades.size();

        List<Double> studentAverages = new ArrayList<>();
        List<Double> subjectAverages = new ArrayList<>();

        // Calculate individual student averages and accumulate subject grades
        for (Student student : students) {
            double totalGrade = 0;

            for (SubjectGrade grade : student.grades) {
                totalGrade += grade.grade;
                subjectAverages.add((double) grade.grade);
            }

            double studentAverage = totalGrade / numSubjects;
            studentAverages.add(studentAverage);
        }

        // Calculate overall average grade
        double overallAverage = calculateAverage(studentAverages);

        // Calculate subject averages by dividing the accumulated grades by the number of students
        for (int i = 0; i < subjectAverages.size(); i++) {
            subjectAverages.set(i, subjectAverages.get(i) / numStudents);
        }

        // Calculate standard deviation of grades across all students
        double stdDeviation = calculateStandardDeviation(studentAverages);

        return new StatisticsResult(studentAverages, subjectAverages, overallAverage, stdDeviation);
    }

    private static double calculateAverage(List<Double> values) {
        double sum = 0;

        for (Double value : values) {
            sum += value;
        }

        return sum / values.size();
    }

    private static double calculateStandardDeviation(List<Double> values) {
        double mean = calculateAverage(values);
        double sumSquaredDiff = 0;

        for (Double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }

        double variance = sumSquaredDiff / values.size();
        return Math.sqrt(variance);
    }

    public static void main(String[] args) {
        List<Student> students = List.of(
                new Student("John Doe", List.of(new SubjectGrade("Math", 90), new SubjectGrade("English", 85),
                        new SubjectGrade("Science", 92), new SubjectGrade("History", 88), new SubjectGrade("Art", 95))),
                new Student("Jane Smith", List.of(new SubjectGrade("Math", 88), new SubjectGrade("English", 92),
                        new SubjectGrade("Science", 87), new SubjectGrade("History", 90), new SubjectGrade("Art", 93))),
                new Student("Bob Johnson", List.of(new SubjectGrade("Math", 78), new SubjectGrade("English", 85),
                        new SubjectGrade("Science", 80), new SubjectGrade("History", 88), new SubjectGrade("Art", 82)))
        );

        StatisticsResult result = calculateGradesStatistics(students);
        System.out.println(result);
    }
}
