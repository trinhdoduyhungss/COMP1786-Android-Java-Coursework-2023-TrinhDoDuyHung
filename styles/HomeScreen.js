import { StyleSheet } from 'react-native';

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
    },
    infoContainer: {
        flexDirection: "column",
        alignItems: "flex-start",
        justifyContent: "space-between",
        textAlignVertical: "center",
    },
    infoHeader: {
        fontSize: 18,
        fontWeight: "bold",
        marginBottom: 4,
    },
    infoBody: {
        fontSize: 12,
    },
    hikeItem: {
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: 12,
        borderWidth: 1,
        borderColor: "#ccc",
        padding: 8,
        borderRadius: 8,
    },
    deleteButton: {
        backgroundColor: "red",
        padding: 8,
        borderRadius: 4,
    },
    deleteButtonText: {
        color: "white",
    },
    addButton: {
        backgroundColor: "green",
        padding: 16,
        borderRadius: 4,
        alignItems: "center",
    },
    cleanButton: {
        backgroundColor: "red",
        padding: 16,
        borderRadius: 4,
        alignItems: "center",
        marginTop: 16,
    },
    addButtonText: {
        color: "white",
        fontWeight: "bold",
    },
});

export default styles;
