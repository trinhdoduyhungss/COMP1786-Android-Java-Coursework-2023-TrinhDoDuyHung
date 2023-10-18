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

const AddScreen = ({ navigation }) => {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [doh, setDoh] = useState("");
    const [hasParking, setHasParking] = useState("");
    const [loh, setLoh] = useState("");
    const [difficulty, setDifficulty] = useState("");
    const [description, setDescription] = useState("");
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

    const handleAddHike = async () => {
        if (!name || !location || !doh || !hasParking || !loh || !difficulty) {
            Alert.alert("Error", "Please enter title and description");
            return;
        }
        await Database.addHike(name, doh, location, hasParking, loh, difficulty, description);
        navigation.goBack();
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
            <TouchableOpacity style={styles.addButton} onPress={handleAddHike}>
                <Text style={styles.addButtonText}>ADD</Text>
            </TouchableOpacity>
        </View>
    );
};

export default AddScreen;