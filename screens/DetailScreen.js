import React, { useState, useMemo } from "react";
import {
    Alert,
    Pressable,
    Text,
    TextInput,
    TouchableOpacity,
    View,
} from "react-native";
import DateTimePicker from '@react-native-community/datetimepicker';
import RadioGroup from 'react-native-radio-buttons-group';
import DropDownPicker from 'react-native-dropdown-picker';
import Database from "../Database/Database";
import styles from "../styles/AddScreen";

const DetailScreen = ({ navigation, route }) => {
    const { hike } = route.params;

    const [name, setName] = useState(hike.name);
    const [location, setLocation] = useState(hike.location);
    const [doh, setDoh] = useState(hike.doh);
    const [hasParking, setHasParking] = useState(hike.hasParking.toString());
    const [loh, setLoh] = useState(hike.loh.toString());
    const [difficulty, setDifficulty] = useState(hike.difficulty);
    const [description, setDescription] = useState(hike.description);
    const [showDatePicker, setShowDatePicker] = useState(false);
    const [date, setDate] = useState(new Date(1697342161000));

    const radioButtons = useMemo(() => ([
        {
            id: '1',
            label: 'Yes',
            value: 'Yes'
        },
        {
            id: '2',
            label: 'No',
            value: 'No'
        }
    ]), []);

    const [open, setOpen] = useState(false);
    const [items, setItems] = useState([
        { label: 'HIGH', value: 'HIGH' },
        { label: 'EASY', value: 'EASY' },
        { label: 'MEDIUM', value: 'MEDIUM' },
    ]);

    const convertBinary2Boolean = (binary) => {
        if (binary == "1") {
            return "True";
        }
        return "False";
    }

    const handleUpdateHike = async () => {
        if (!name || !location || !doh || !hasParking || !loh || !difficulty) {
            Alert.alert("Error", "Please enter all required fields!");
            return;
        }
        let full_information = ""
        full_information = full_information + "Name: " + name + "\n"
        full_information = full_information + "Location: " + location + "\n"
        full_information = full_information + "Date of hike: " + doh + "\n"
        full_information = full_information + "Parking available: " + convertBinary2Boolean(hasParking) + "\n"
        full_information = full_information + "Length of hike: " + loh + "\n"
        full_information = full_information + "Difficulty level: " + difficulty + "\n"
        full_information = full_information + "Description: " + description + "\n"
        Alert.alert(
            "Confirmation",
            "Are you sure you want to add this hike?\n"+full_information,
            [
                {
                    text: "Cancel",
                    onPress: () => console.log("Cancel Pressed"),
                    style: "cancel"
                },
                { text: "OK", onPress: async () => {
                    await Database.updateHike(hike.id, name, doh, location, hasParking, loh, difficulty, description);
                    navigation.goBack();
                } }
            ]
        );
    };

    const onChangeDate = (event, selectedDate) => {
        const currentDate = selectedDate;
        setShowDatePicker(false);
        setDate(currentDate);
        let dateString = currentDate.getDate() + "/" + (currentDate.getMonth() + 1) + "/" + currentDate.getFullYear();
        setDoh(dateString);
    };

    const showDatepicker = () => {
        setShowDatePicker(true);
    };

    return (
        <View style={styles.container}>
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Name of the hike:</Text>
                <Text style={styles.required}>*</Text>
            </View>
            <TextInput
                style={styles.input}
                value={name}
                onChangeText={setName}
                placeholder="Son Dong"
            />
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Location:</Text>
                <Text style={styles.required}>*</Text>
            </View>
            <TextInput
                style={styles.input}
                value={location}
                onChangeText={setLocation}
                placeholder="Quang Binh"
                multiline
            />
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Date of the hike:</Text>
                <Text style={styles.required}>*</Text>
            </View>
            <Pressable onPress={showDatepicker}>
                <TextInput
                    style={styles.input}
                    value={doh}
                    placeholder="20/11/2023"
                    editable={false}
                    multiline
                />
            </Pressable>
            {showDatePicker && (
                <DateTimePicker
                    testID="dateTimePicker"
                    value={date}
                    mode={'date'}
                    is24Hour={true}
                    onChange={onChangeDate}
                />
            )}
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Parking available :</Text>
                <Text style={styles.required}>*</Text>
                <RadioGroup
                    radioButtons={radioButtons}
                    onPress={setHasParking}
                    selectedId={hasParking}
                    layout="row"
                />
            </View>
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Length of the hike:</Text>
                <Text style={styles.required}>*</Text>
                <TextInput
                    style={styles.input}
                    value={loh}
                    onChangeText={setLoh}
                    placeholder="100"
                    multiline={false}
                    keyboardType="numeric"
                />
            </View>
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Difficulty level:</Text>
                <Text style={styles.required}>*</Text>
            </View>
            <DropDownPicker
                open={open}
                value={difficulty}
                items={items}
                setOpen={setOpen}
                setValue={setDifficulty}
                setItems={setItems}
                style={styles.input}
            />
            <View style={styles.labelContainer}>
                <Text style={styles.label}>Description:</Text>
            </View>
            <TextInput
                style={styles.input}
                value={description}
                onChangeText={setDescription}
                placeholder="Description about the hike"
                multiline
            />
            <TouchableOpacity style={styles.addButton} onPress={handleUpdateHike}>
                <Text style={styles.addButtonText}>UPDATE</Text>
            </TouchableOpacity>
        </View>
    );
};

export default DetailScreen;