import { useIsFocused } from "@react-navigation/native";
import React, { useEffect, useState } from "react";
import {
    Alert,
    FlatList,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
} from "react-native";

import styles from "../styles/HomeScreen";
import Database from "../Database/Database";

const HomeScreen = ({ navigation }) => {
    const [Hikes, setHikes] = useState([]);
    const isFocused = useIsFocused();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await Database.getHikes();
                setHikes(data);
            } catch (error) {
                console.log("Error fetching Hikes", error);
            }
        };

        fetchData();
    }, [isFocused]);

    const handleDeleteHike = async (id) => {
        await Database.deleteHike(id);
        const data = await Database.getHikes();
        setHikes(data);
    };

    const handleCleanHikes = async () => {
        await Database.cleanHikes();
        setHikes([]);
        Alert.alert("All Hikes deleted successfully.");
    };

    const renderHikeItem = ({ item }) => (
        <TouchableOpacity
            style={styles.hikeItem}
            onPress={() => navigation.navigate("Edit hike", { hike: item })}
        >
            <View style={styles.infoContainer}>
                <Text style={styles.infoHeader}>{item.name}</Text>
                <Text style={styles.infoBody}>{item.doh}</Text>
            </View>
            <TouchableOpacity
                style={styles.deleteButton}
                onPress={() => handleDeleteHike(item.id)}
            >
                <Text style={styles.deleteButtonText}>Delete</Text>
            </TouchableOpacity>
        </TouchableOpacity>
    );

    return (
        <View style={styles.container}>
            <FlatList
                data={Hikes}
                renderItem={renderHikeItem}
                keyExtractor={(item) => item.id.toString()}
                scrollEnabled={true}
            />
            <TouchableOpacity
                style={styles.addButton}
                onPress={() => navigation.navigate("Add new hike")}
            >
                <Text style={styles.addButtonText}>New Hike</Text>
            </TouchableOpacity>
            <TouchableOpacity
                style={styles.cleanButton}
                onPress={() => handleCleanHikes()}
            >
                <Text style={styles.addButtonText}>Clear All Hike</Text>
            </TouchableOpacity>
        </View>
    );
};

export default HomeScreen;
