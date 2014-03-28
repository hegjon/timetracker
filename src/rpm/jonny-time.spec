Name: jonny-time
Version: 1.0
Release: 1%{?dist}
Summary: Jonny time tracker

License: ASL 2.0
URL: http://jonnyware.com

Source0: %{name}.tar.gz
BuildArch: noarch

BuildRequires: java-devel >= 1:1.5.0
BuildRequires: maven-local
Requires:      java-headless >= 1:1.5.0

BuildRequires: joda-time
BuildRequires: apache-commons-lang3
BuildRequires: snakeyaml
BuildRequires: junit >= 4
BuildRequires: hamcrest
Requires:      apache-commons-codec

%description
Hmm..

%package javadoc
Summary: Javadoc for %{name}

%description javadoc
API documentation for %{name}.

%prep

%setup -c

%build
%mvn_build

%install
%mvn_install

%jpackage_script com.jonnyware.timetracker.Main "" "" jonny-time:joda-time:apache-commons-lang3:snakeyaml:apache-commons-codec jtime true
%jpackage_script com.jonnyware.timetracker.JTimeTotalDiff "" "" jonny-time:joda-time:apache-commons-lang3:snakeyaml:apache-commons-codec jtime-total-diff true

%files -f .mfiles
%{_bindir}/jtime
%{_bindir}/jtime-total-diff
%doc LICENSE.txt

%files javadoc -f .mfiles-javadoc
%doc LICENSE.txt

%changelog
* Tue Mar 25 2014 Jonny Heggheim <hegjon@gmail.com> - 1.0-1
- Initial packaging
