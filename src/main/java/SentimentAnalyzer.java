import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.DefaultXYDataset;

public class Smi extends JFrame {

    private JTextArea inputTextArea;
    private JTextArea outputTextArea;
    private JButton analyzeButton;
    private ChartPanel lineChartPanel;
    private ChartPanel barChartPanel;
    private ChartPanel pieChartPanel;
    private JTable outputTable;
    private String overallSentiment;

    private JLabel count;


    public Smi() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JLabel headingLabel = new JLabel("Sentiment Analyzer by Suraj Bangade");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);


        inputTextArea = new JTextArea();
        inputTextArea.setText("Input text here");
        JScrollPane inputScrollPane = new JScrollPane(inputTextArea);
        inputTextArea.setLineWrap(true);
        add(inputScrollPane, BorderLayout.CENTER);

        initializeOutputTable();
        JScrollPane outputScrollPane = new JScrollPane(outputTable);
        outputScrollPane.setPreferredSize(new Dimension(0, 200));
        add(outputScrollPane, BorderLayout.SOUTH);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(300, 400));
        add(tabbedPane, BorderLayout.WEST);

        DefaultPieDataset emptyPieDataset = new DefaultPieDataset();
        JFreeChart emptyPieChart = ChartFactory.createPieChart("", emptyPieDataset, true, true, false);
        pieChartPanel = new ChartPanel(emptyPieChart);
        pieChartPanel.setPreferredSize(new Dimension(300, 100));

        DefaultXYDataset emptyLineDataset = new DefaultXYDataset();
        JFreeChart emptyLineChart = ChartFactory.createXYLineChart("", "", "", emptyLineDataset);
        lineChartPanel = new ChartPanel(emptyLineChart);

        DefaultCategoryDataset emptyBarDataset = new DefaultCategoryDataset();
        JFreeChart emptyBarChart = ChartFactory.createBarChart("", "", "", emptyBarDataset);
        barChartPanel = new ChartPanel(emptyBarChart);

        count = new JLabel();
        count.setFont(new Font("syrill",Font.BOLD,50));

        tabbedPane.addTab("Pie Chart", pieChartPanel);
        tabbedPane.addTab("Line Chart", lineChartPanel);
        tabbedPane.addTab("Bar Chart", barChartPanel);
        tabbedPane.addTab("Overall Sentiment",count);

        analyzeButton = new JButton("Analyze");
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputTextArea.getText();
                String[] sentences = preprocessText(inputText);

                DefaultPieDataset pieDataset = createPieDataset(sentences);
                DefaultXYDataset lineDataset = createLineDataset(sentences);
                DefaultCategoryDataset barDataset = createBarDataset(sentences);

                JFreeChart pieChart = createPieChart(pieDataset);
                JFreeChart lineChart = createLineChart(lineDataset);
                JFreeChart barChart = createBarChart(barDataset);

                pieChartPanel.setChart(pieChart);
                lineChartPanel.setChart(lineChart);
                barChartPanel.setChart(barChart);
                count.setText(overallSentiment);

                DefaultTableModel tableModel = (DefaultTableModel) outputTable.getModel();
                tableModel.setRowCount(0); // Clear previous data

                StringBuilder positiveSentences = new StringBuilder();
                StringBuilder negativeSentences = new StringBuilder();
                StringBuilder neutralSentences = new StringBuilder();

                for (String s : sentences) {
                    String sentiment = predictSentiment(s);
                    if (sentiment.equals("Positive")) {
                        positiveSentences.append(s).append("<br>");
                    } else if (sentiment.equals("Negative")) {
                        negativeSentences.append(s).append("<br>");
                    } else {
                        neutralSentences.append(s).append("<br>");
                    }
                }


                Object[] rowData = {"Positive", "<html>" + positiveSentences.toString() + "</html>"};
                tableModel.addRow(rowData);

                rowData = new Object[]{"Negative", "<html>" + negativeSentences.toString() + "</html>"};
                tableModel.addRow(rowData);

                rowData = new Object[]{"Neutral", "<html>" + neutralSentences.toString() + "</html>"};
                tableModel.addRow(rowData);
            }
        });
        add(analyzeButton, BorderLayout.EAST);


        // Set JFrame size and make it visible
        setSize(800, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private String calculateOverallSentiment(int positiveCount, int negativeCount, int neutralCount) {
        if (positiveCount > negativeCount && positiveCount > neutralCount) {
            return "Positive";
        } else if (negativeCount > positiveCount && negativeCount > neutralCount) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }


    private String[] preprocessText(String inputText) {
        TextPreprocessor tp = new TextPreprocessor();
        String[] sentences = tp.detectSentences(inputText);
        return sentences;
    }

    private String predictSentiment(String preprocessedText) {
        if (preprocessedText.contains("good") || preprocessedText.contains("happy") || preprocessedText.contains("excellent") ||
                preprocessedText.contains("amazing") || preprocessedText.contains("awesome") || preprocessedText.contains("wonderful") ||
                preprocessedText.contains("fantastic") || preprocessedText.contains("great") || preprocessedText.contains("terrific") ||
                preprocessedText.contains("love") || preprocessedText.contains("joyful") || preprocessedText.contains("pleasure") ||
                preprocessedText.contains("satisfied") || preprocessedText.contains("delighted") || preprocessedText.contains("grateful") ||
                preprocessedText.contains("glad") || preprocessedText.contains("content") || preprocessedText.contains("thrilled") ||
                preprocessedText.contains("ecstatic") || preprocessedText.contains("cheerful") || preprocessedText.contains("blessed") ||
                preprocessedText.contains("uplifting") || preprocessedText.contains("optimistic") || preprocessedText.contains("vibrant") ||
                preprocessedText.contains("euphoric") || preprocessedText.contains("wonder") || preprocessedText.contains("radiant") ||
                preprocessedText.contains("blissful") || preprocessedText.contains("graceful") || preprocessedText.contains("exhilarated")) {
            return "Positive";
        } else if (preprocessedText.contains("bad") || preprocessedText.contains("sad") || preprocessedText.contains("terrible") ||
                preprocessedText.contains("awful") || preprocessedText.contains("horrible") || preprocessedText.contains("miserable") ||
                preprocessedText.contains("disappointing") || preprocessedText.contains("disgusting") || preprocessedText.contains("hate") ||
                preprocessedText.contains("upset") || preprocessedText.contains("frustrated") || preprocessedText.contains("angry") ||
                preprocessedText.contains("annoyed") || preprocessedText.contains("depressed") || preprocessedText.contains("regretful") ||
                preprocessedText.contains("displeased") || preprocessedText.contains("irritated") || preprocessedText.contains("miserable") ||
                preprocessedText.contains("unhappy") || preprocessedText.contains("distressed") || preprocessedText.contains("heartbreaking") ||
                preprocessedText.contains("tragic") || preprocessedText.contains("devastating") || preprocessedText.contains("gloomy") ||
                preprocessedText.contains("despair") || preprocessedText.contains("painful") || preprocessedText.contains("woeful") ||
                preprocessedText.contains("melancholy") || preprocessedText.contains("sorrowful")) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    private void initializeOutputTable() {
        // Define table column names
        String[] columnNames = {"Sentiment", "Sentence"};

        // Create table data
        Object[][] data = {
                {"Positive", ""},
                {"Negative", ""},
                {"Neutral", ""}
        };

        // Create the table model with data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create the table using the table model
        outputTable = new JTable(tableModel);
        outputTable.setRowHeight(50);
    }

    private DefaultPieDataset createPieDataset(String[] sentences) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;

        for (String sentence : sentences) {
            String sentiment = predictSentiment(sentence);
            if (sentiment.equals("Positive")) {
                positiveCount++;
            } else if (sentiment.equals("Negative")) {
                negativeCount++;
            } else {
                neutralCount++;
            }
        }

        dataset.setValue("Positive", positiveCount);
        dataset.setValue("Negative", negativeCount);
        dataset.setValue("Neutral", neutralCount);

        overallSentiment = calculateOverallSentiment(positiveCount, negativeCount, neutralCount);


        return dataset;
    }

    private DefaultXYDataset createLineDataset(String[] sentences) {
        DefaultXYDataset dataset = new DefaultXYDataset();
        double[][] data = new double[2][sentences.length];

        for (int i = 0; i < sentences.length; i++) {
            String sentiment = predictSentiment(sentences[i]);
            double yValue = 0.0;

            if (sentiment.equals("Positive")) {
                yValue = 1.0;
            } else if (sentiment.equals("Negative")) {
                yValue = -1.0;
            }

            data[0][i] = i;
            data[1][i] = yValue;
        }

        dataset.addSeries("Sentiment", data);
        return dataset;
    }

    // Create the line chart
    private JFreeChart createLineChart(DefaultXYDataset dataset) {
        JFreeChart chart = ChartFactory.createXYLineChart("Sentiment Distribution", "Sentence", "Sentiment", dataset);
        return chart;
    }

    // Create the bar chart dataset
    private DefaultCategoryDataset createBarDataset(String[] sentences) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;

        for (int i = 0; i < sentences.length; i++) {
            String sentiment = predictSentiment(sentences[i]);

            if (sentiment.equals("Positive")) {
                positiveCount++;
            } else if (sentiment.equals("Negative")) {
                negativeCount++;
            } else {
                neutralCount++;
            }
        }

        dataset.addValue(positiveCount, "Count", "Positive");
        dataset.addValue(negativeCount, "Count", "Negative");
        dataset.addValue(neutralCount, "Count", "Neutral");

        return dataset;
    }

    // Create the bar chart
    private JFreeChart createBarChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart("Sentiment Distribution", "Sentiment", "Count", dataset);
        return chart;
    }


    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createBarChart("Sentiment Distribution", "Sentiment", "Count", dataset);
        return chart;
    }

    private JFreeChart createPieChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("Sentiment Distribution", dataset, true, true, false);
        return chart;
    }
}
