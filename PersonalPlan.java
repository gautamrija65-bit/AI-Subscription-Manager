// PersonalPlan extends AIModel
class PersonalPlan extends AIModel {
    private int promptsRemaining; // Monthly prompt quota

    // Constructor
    public PersonalPlan(String modelName, double price, int parameterCount, int contextWindow, int promptsRemaining) {
        super(modelName, price, parameterCount, contextWindow);
        this.promptsRemaining = promptsRemaining;
    }

    // Getter for remaining prompts
    public int getPromptsRemaining() {
        return promptsRemaining;
    }

    // Purchase additional prompts
    public String purchasePrompts(int additionalPrompts) {
        if (additionalPrompts <= 0) {
            return "Error: You must enter a positive value or upgrade to Pro Plan.";
        }
        promptsRemaining += additionalPrompts;
        return "Successfully added " + additionalPrompts + " prompts. Total remaining: " + promptsRemaining;
    }

    // Use a prompt (make API call)
    public String usePrompt(String promptText, int outputTokens) {
        if (promptsRemaining <= 0) {
            return "Error: Monthly plan quota has been reached. Please upgrade or purchase additional prompts.";
        }
        
        int inputTokens = promptText.length();
        int totalTokens = inputTokens + outputTokens;
        
        if (totalTokens > getContextWindow()) {
            return "Error: Request exceeds context window limit.";
        }
        
        promptsRemaining--;
        return "✓ Prompt Accepted\n" +
               "  Input Tokens: " + inputTokens + "\n" +
               "  Output Tokens: " + outputTokens + "\n" +
               "  Total Tokens Used: " + totalTokens + "\n" +
               "  Prompts Remaining: " + promptsRemaining;
    }

    // Display PersonalPlan details
    @Override
    public String display() {
        return super.display() + " | Prompts Remaining: " + promptsRemaining;
    }
}