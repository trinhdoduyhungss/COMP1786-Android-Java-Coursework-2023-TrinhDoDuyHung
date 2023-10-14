import { StyleSheet } from 'react-native';
const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
    },
    labelContainer: {
        flexDirection: "row",
        alignItems: "center",
        marginBottom: 8,
    },
    label: {
        fontWeight: "bold",
        marginRight: 8,
    },
    required: {
        color: "red",
        fontSize: 16,
        marginEnd: 8,
    },
    input: {
        borderWidth: 1,
        borderColor: "#ccc",
        borderRadius: 4,
        marginBottom: 16,
        padding: 8,
    },
    addButton: {
        backgroundColor: "green",
        padding: 16,
        borderRadius: 4,
        alignItems: "center",
    },
    addButtonText: {
        color: "white",
        fontWeight: "bold",
    },
});
export default styles;