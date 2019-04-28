package org.angmarc.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.SimpleSpinnerTextFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDefault();
        setupTintedWithCustomClass();
        setupXml();

        testFixItem();
        testFixItemWithNoData();
    }

    private void testFixItem() {
        NiceSpinner spinner = findViewById(R.id.fix_item_nice_spinner);
        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Tony", "Stark"));
        persons.add(new Person("Steve", "Rogers"));
        persons.add(new Person("Bruce", "Banner"));
        spinner.attachDataSource(persons);

        spinner.setSimpleCallback((v) -> {
            Toast.makeText(MainActivity.this, "position: " + v + ", bean -> " + persons.get(v), Toast.LENGTH_SHORT).show();
        });
    }

    private void testFixItemWithNoData() {
        NiceSpinner spinner = findViewById(R.id.no_data_nice_spinner);
        spinner.attachDataSource(new ArrayList<String>());
    }

    private void setupXml() {
        NiceSpinner spinner = findViewById(R.id.niceSpinnerXml);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void setupTintedWithCustomClass() {
        final NiceSpinner spinner = findViewById(R.id.tinted_nice_spinner);
        List<Person> persons = new ArrayList<>();

        persons.add(new Person("Tony", "Stark"));
        persons.add(new Person("Steve", "Rogers"));
        persons.add(new Person("Bruce", "Banner"));

        SimpleSpinnerTextFormatter textFormatter = new SimpleSpinnerTextFormatter() {
            @Override
            public Spannable format(Object item) {
                Person person = (Person) item;
                return new SpannableString(person.getName() + " " + person.getSurname());
            }
        };

        spinner.setSpinnerTextFormatter(textFormatter);
        spinner.setSelectedTextFormatter(textFormatter);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = (Person) spinner.getSelectedItem(); //parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected: " + person.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        spinner.attachDataSource(persons);
    }

    private void setupDefault() {
        NiceSpinner spinner = findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        spinner.attachDataSource(dataset);
        spinner.addOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this, "Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

class Person {

    private String name;
    private String surname;

    Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    String getName() {
        return name;
    }

    String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}
