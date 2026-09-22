// ProPlan extends AIModel
import java.util.ArrayList;

class ProPlan extends AIModel {
    private int availableSlots;
    private ArrayList<String> teamMembers; // Track actual team members

    // Constructor
    public ProPlan(String modelName, double price, int parameterCount, int contextWindow, int availableSlots) {
        super(modelName, price, parameterCount, contextWindow);
        this.availableSlots = availableSlots;
        this.teamMembers = new ArrayList<>();
    }

    // Getter for available slots
    public int getAvailableSlots() {
        return availableSlots;
    }

    // Getter for team members list
    public ArrayList<String> getTeamMembers() {
        return teamMembers;
    }

    // Add a team member
    public String addTeamMember(String memberName) {
        if (memberName == null || memberName.trim().isEmpty()) {
            return "Error: Team member name cannot be empty.";
        }
        if (teamMembers.contains(memberName)) {
            return "Error: " + memberName + " is already a team member.";
        }
        if (availableSlots <= 0) {
            return "Error: No available slots for team members.";
        }
        
        teamMembers.add(memberName);
        availableSlots--;
        return "✓ " + memberName + " added to team. Available slots: " + availableSlots;
    }

    // Remove a team member
    public String removeTeamMember(String memberName) {
        if (memberName == null || memberName.trim().isEmpty()) {
            return "Error: Team member name cannot be empty.";
        }
        if (teamMembers.contains(memberName)) {
            teamMembers.remove(memberName);
            availableSlots++;
            return "✓ " + memberName + " removed from team. Available slots: " + availableSlots;
        }
        return "Error: " + memberName + " is not a team member.";
    }

    // Display ProPlan details
    @Override
    public String display() {
        String teamList = teamMembers.isEmpty() ? "None" : String.join(", ", teamMembers);
        return super.display() + 
               " | Team Slots: " + availableSlots + 
               " | Team Members: " + teamList;
    }
}