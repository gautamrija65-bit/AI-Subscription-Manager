import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class SubscriptionGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField modelNameField, priceField, parameterField, contextField, quotaField, slotsField;
    private JTextField indexField, promptField, responseLengthField, memberNameField, purchasePromptsField;
    private ArrayList<AIModel> plans;

    public SubscriptionGUI() {
        setTitle("AI Subscription Manager");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        plans = new ArrayList<>();

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Model Name:"));
        modelNameField = new JTextField();
        inputPanel.add(modelNameField);

        inputPanel.add(new JLabel("Price (NPR):"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Parameters (B):"));
        parameterField = new JTextField();
        inputPanel.add(parameterField);

        inputPanel.add(new JLabel("Context Window (K):"));
        contextField = new JTextField();
        inputPanel.add(contextField);

        inputPanel.add(new JLabel("Prompt Quota (Personal):"));
        quotaField = new JTextField();
        inputPanel.add(quotaField);

        inputPanel.add(new JLabel("Team Slots (Pro):"));
        slotsField = new JTextField();
        inputPanel.add(slotsField);

        add(inputPanel, BorderLayout.NORTH);

        // Control panel - increased grid to fit all buttons
        JPanel controlPanel = new JPanel(new GridLayout(6, 2));

        JButton addPersonalButton = new JButton("Add Personal Plan");
        JButton addProButton = new JButton("Add Pro Plan");
        JButton displayAllButton = new JButton("Display All");
        JButton clearButton = new JButton("Clear");
        JButton givePromptButton = new JButton("Give a Prompt");
        JButton addTeamButton = new JButton("Add Team Member");
        JButton removeTeamButton = new JButton("Remove Team Member");
        JButton purchasePromptsButton = new JButton("Purchase Prompts");
        JButton checkTypeButton = new JButton("Check Plan Type");
        JButton exportButton = new JButton("Export to File");
        JButton loadButton = new JButton("Load From File");

        controlPanel.add(addPersonalButton);
        controlPanel.add(addProButton);
        controlPanel.add(displayAllButton);
        controlPanel.add(clearButton);
        controlPanel.add(givePromptButton);
        controlPanel.add(addTeamButton);
        controlPanel.add(removeTeamButton);
        controlPanel.add(purchasePromptsButton);
        controlPanel.add(checkTypeButton);
        controlPanel.add(exportButton);
        controlPanel.add(loadButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Extra input fields for actions
        JPanel actionPanel = new JPanel(new GridLayout(5, 2));
        actionPanel.add(new JLabel("Index Number:"));
        indexField = new JTextField();
        actionPanel.add(indexField);

        actionPanel.add(new JLabel("Prompt Text:"));
        promptField = new JTextField();
        actionPanel.add(promptField);

        actionPanel.add(new JLabel("Response Length:"));
        responseLengthField = new JTextField();
        actionPanel.add(responseLengthField);

        actionPanel.add(new JLabel("Team Member Name:"));
        memberNameField = new JTextField();
        actionPanel.add(memberNameField);

        actionPanel.add(new JLabel("Purchase Prompts:"));
        purchasePromptsField = new JTextField();
        actionPanel.add(purchasePromptsField);

        add(actionPanel, BorderLayout.EAST);

        // Action Listeners
        addPersonalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = modelNameField.getText().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("Model name cannot be empty");
                    
                    double price = Double.parseDouble(priceField.getText());
                    if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
                    
                    int params = Integer.parseInt(parameterField.getText());
                    if (params < 0) throw new IllegalArgumentException("Parameter count cannot be negative");
                    
                    int context = Integer.parseInt(contextField.getText());
                    if (context < 0) throw new IllegalArgumentException("Context window cannot be negative");
                    
                    int quota = Integer.parseInt(quotaField.getText());
                    if (quota < 0) throw new IllegalArgumentException("Quota cannot be negative");
                    
                    PersonalPlan plan = new PersonalPlan(name, price, params, context, quota);
                    plans.add(plan);
                    outputArea.append("✓ Personal Plan added at index " + (plans.size() - 1) + "\n");
                    clearFieldsAction();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Invalid input format. Please check your entries.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        ex.getMessage(), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addProButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = modelNameField.getText().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("Model name cannot be empty");
                    
                    double price = Double.parseDouble(priceField.getText());
                    if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
                    
                    int params = Integer.parseInt(parameterField.getText());
                    if (params < 0) throw new IllegalArgumentException("Parameter count cannot be negative");
                    
                    int context = Integer.parseInt(contextField.getText());
                    if (context < 0) throw new IllegalArgumentException("Context window cannot be negative");
                    
                    int slots = Integer.parseInt(slotsField.getText());
                    if (slots < 0) throw new IllegalArgumentException("Team slots cannot be negative");
                    
                    ProPlan plan = new ProPlan(name, price, params, context, slots);
                    plans.add(plan);
                    outputArea.append("✓ Pro Plan added at index " + (plans.size() - 1) + "\n");
                    clearFieldsAction();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Invalid input format. Please check your entries.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        ex.getMessage(), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayAllButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (plans.isEmpty()) {
                    outputArea.append("No plans available.\n");
                } else {
                    outputArea.append("\n========== ALL SUBSCRIPTION PLANS ==========\n");
                    for (int i = 0; i < plans.size(); i++) {
                        outputArea.append("[Index " + i + "] " + plans.get(i).display() + "\n");
                    }
                    outputArea.append("==========================================\n\n");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFieldsAction();
                outputArea.append("All fields cleared.\n");
            }
        });

        givePromptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idx = getDisplayNumber();
                    if (idx != -1) {
                        AIModel plan = plans.get(idx);
                        String prompt = promptField.getText();
                        if (prompt.isEmpty()) throw new IllegalArgumentException("Prompt text cannot be empty");
                        
                        int length = Integer.parseInt(responseLengthField.getText());
                        if (length <= 0) throw new IllegalArgumentException("Response length must be positive");
                        
                        if (plan instanceof PersonalPlan) {
                            String result = ((PersonalPlan) plan).usePrompt(prompt, length);
                            outputArea.append(result + "\n");
                        } else {
                            JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                                "Only Personal Plan supports prompts.\n\nPro Plan users have unlimited prompt access.", 
                                "Plan Type Mismatch", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Response length must be a valid integer.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        ex.getMessage(), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idx = getDisplayNumber();
                    if (idx != -1) {
                        AIModel plan = plans.get(idx);
                        String member = memberNameField.getText().trim();
                        if (member.isEmpty()) throw new IllegalArgumentException("Team member name cannot be empty");
                        
                        if (plan instanceof ProPlan) {
                            String result = ((ProPlan) plan).addTeamMember(member);
                            outputArea.append(result + "\n");
                        } else {
                            JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                                "Team collaboration is only available for Pro Plan subscriptions.", 
                                "Plan Type Mismatch", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        ex.getMessage(), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeTeamButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idx = getDisplayNumber();
                    if (idx != -1) {
                        AIModel plan = plans.get(idx);
                        String member = memberNameField.getText().trim();
                        if (member.isEmpty()) throw new IllegalArgumentException("Team member name cannot be empty");
                        
                        if (plan instanceof ProPlan) {
                            String result = ((ProPlan) plan).removeTeamMember(member);
                            outputArea.append(result + "\n");
                        } else {
                            JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                                "Team management is only available for Pro Plan subscriptions.", 
                                "Plan Type Mismatch", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        ex.getMessage(), 
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        purchasePromptsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idx = getDisplayNumber();
                    if (idx != -1) {
                        AIModel plan = plans.get(idx);
                        int prompts = Integer.parseInt(purchasePromptsField.getText());
                        
                        if (plan instanceof PersonalPlan) {
                            String result = ((PersonalPlan) plan).purchasePrompts(prompts);
                            outputArea.append(result + "\n");
                        } else {
                            JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                                "Prompt purchase is only available for Personal Plan subscriptions.\n\nPro Plan users have unlimited access.", 
                                "Plan Type Mismatch", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Prompt count must be a valid integer.", 
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        checkTypeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int idx = getDisplayNumber();
                    if (idx != -1) {
                        AIModel plan = plans.get(idx);
                        String planType = "Unknown";
                        if (plan instanceof PersonalPlan) {
                            planType = "Personal Plan";
                        } else if (plan instanceof ProPlan) {
                            planType = "Pro Plan";
                        }
                        outputArea.append("Index " + idx + " → " + planType + "\n");
                    }
                } catch (Exception ex) {
                    // Already handled by getDisplayNumber()
                }
            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (plans.isEmpty()) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "No plans to export.", 
                        "Export Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try (FileWriter writer = new FileWriter("subscription_plans.txt")) {
                    writer.write("===== AI SUBSCRIPTION PLANS EXPORT =====\n");
                    writer.write("Total Plans: " + plans.size() + "\n\n");
                    for (int i = 0; i < plans.size(); i++) {
                        writer.write("Index " + i + ": " + plans.get(i).display() + "\n");
                        if (plans.get(i) instanceof ProPlan) {
                            ProPlan proPlan = (ProPlan) plans.get(i);
                            writer.write("  Team Members: " + proPlan.getTeamMembers() + "\n");
                            writer.write("  Available Slots: " + proPlan.getAvailableSlots() + "\n");
                        } else if (plans.get(i) instanceof PersonalPlan) {
                            PersonalPlan persPlan = (PersonalPlan) plans.get(i);
                            writer.write("  Prompts Remaining: " + persPlan.getPromptsRemaining() + "\n");
                        }
                        writer.write("\n");
                    }
                    writer.write("=========================================\n");
                    outputArea.append("✓ Plans successfully exported to 'subscription_plans.txt'\n");
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Plans exported successfully to subscription_plans.txt", 
                        "Export Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Error exporting to file: " + ex.getMessage(), 
                        "Export Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("subscription_plans.txt"))) {
                    outputArea.append("\n========== LOADING FROM FILE ==========\n");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputArea.append(line + "\n");
                    }
                    outputArea.append("=======================================\n\n");
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Plans loaded successfully.", 
                        "Load Successful", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(SubscriptionGUI.this, 
                        "Error loading from file: subscription_plans.txt not found.", 
                        "Load Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Validated display number input method
    private int getDisplayNumber() {
        int displayNumber = -1;
        try {
            String input = indexField.getText().trim();
            if (input.isEmpty()) throw new NumberFormatException("Index field is empty");
            
            displayNumber = Integer.parseInt(input);
            
            if (displayNumber < 0 || displayNumber >= plans.size()) {
                JOptionPane.showMessageDialog(this, 
                    "Index must be between 0 and " + (plans.size() - 1) + ".", 
                    "Invalid Index Range", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid integer for the index.", 
                "Invalid Index Format", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return displayNumber;
    }

    // Helper method to clear all text fields
    private void clearFieldsAction() {
        modelNameField.setText("");
        priceField.setText("");
        parameterField.setText("");
        contextField.setText("");
        quotaField.setText("");
        slotsField.setText("");
        indexField.setText("");
        promptField.setText("");
        responseLengthField.setText("");
        memberNameField.setText("");
        purchasePromptsField.setText("");
    }

    public static void main(String[] args) {
        SubscriptionGUI gui = new SubscriptionGUI();
        gui.setVisible(true);
    }
}