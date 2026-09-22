// Parent Class AIModel
class AIModel {
    private String modelName;
    private double price; // NPR per 1 Lakh tokens
    private int parameterCount; // in billions
    private int contextWindow; // in tokens (e.g., 64K)
    
    // Constructor
    public AIModel(String modelName, double price, int parameterCount, int contextWindow) {
        this.modelName = modelName;
        this.price = price;
        this.parameterCount = parameterCount;
        this.contextWindow = contextWindow;
    }

    // Getter methods
    public String getModelName() { 
        return modelName; 
    }
    
    public double getPrice() { 
        return price; 
    }
    
    public int getParameterCount() { 
        return parameterCount; 
    }
    
    public int getContextWindow() { 
        return contextWindow; 
    }

    // Display method - returns model details as String
    public String display() {
        return "Model: " + modelName + 
               " | Price: NPR " + price + 
               " | Parameters: " + parameterCount + "B" + 
               " | Context Window: " + contextWindow + "K tokens";
    }
}